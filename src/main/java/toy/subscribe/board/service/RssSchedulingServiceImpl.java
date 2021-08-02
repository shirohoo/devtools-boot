package toy.subscribe.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.board.factory.FeedBoardFactory;
import toy.subscribe.board.model.FeedBoard;
import toy.subscribe.board.model.RSSFeed;
import toy.subscribe.board.model.RSSFeedMessage;
import toy.subscribe.board.parser.JsonReader;
import toy.subscribe.board.parser.RSSFeedParser;
import toy.subscribe.board.repository.FeedBoardRepository;

import javax.xml.stream.XMLStreamException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssSchedulingServiceImpl implements SchedulingService {
    private final FeedBoardFactory feedBoardFactory;
    private final FeedBoardRepository feedBoardRepository;

    @Transactional
    public void allGroupFeedCollect() {
        List<String> urls = JsonReader.readUrls();
        if (Objects.nonNull(urls)) {
            feedBoardRepository.saveAll(urls.stream()
                                            .map(this::getRssFeeds)
                                            .filter(Objects::nonNull)
                                            .flatMap(this::getMessageStreams)
                                            .map(feedBoardFactory::getFeedBoard)
                                            .filter(Objects::nonNull)
                                            .collect(toList()))
                               .forEach(this::loggingNewFeeds);
            return;
        }
        log.error("Urls is null. please check JsonReader !");
    }

    private RSSFeed getRssFeeds(String url) {
        return readRssFeed(createRssFeedParser(url));
    }

    private RSSFeed readRssFeed(RSSFeedParser parser) {
        try {
            return parser.readFeed();
        }
        catch (XMLStreamException e) {
            log.error("XML parsing error.");
        }
        return null;
    }

    private RSSFeedParser createRssFeedParser(String url) {
        try {
            return new RSSFeedParser(url);
        }
        catch (MalformedURLException e) {
            log.error("Syntax not found : 'http' or 'https'.");
        }
        return null;
    }

    private Stream<? extends RSSFeedMessage> getMessageStreams(RSSFeed rssFeed) {
        return rssFeed.getMessages()
                      .stream();
    }

    private void loggingNewFeeds(FeedBoard feedBoard) {
        log.info("[New feed] {}", feedBoard.toString());
    }
}
