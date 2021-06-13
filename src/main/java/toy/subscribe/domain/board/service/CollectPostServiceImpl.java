package toy.subscribe.domain.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.board.factory.FeedBoardFactory;
import toy.subscribe.domain.board.model.FeedBoard;
import toy.subscribe.domain.board.model.RSSFeed;
import toy.subscribe.domain.board.parser.JsonReader;
import toy.subscribe.domain.board.parser.RSSFeedParser;
import toy.subscribe.domain.board.repository.FeedBoardRepository;

import javax.xml.stream.XMLStreamException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectPostServiceImpl implements CollectPostService {
    private final FeedBoardFactory feedBoardFactory;
    private final FeedBoardRepository feedBoardRepository;
    
    @Transactional
    public void allGroupFeedCollect() {
        RSSFeed feed = null;
        List<String> urls = JsonReader.readUrls();
        
        if(urls != null) {
            List<FeedBoard> feedBoards = new ArrayList<>();
            for(String url : urls) {
                try {
                    RSSFeedParser parser = new RSSFeedParser(url);
                    feed = parser.readFeed();
                }
                catch(XMLStreamException e) {
                    log.error("XML parsing error.");
                }
                catch(MalformedURLException e) {
                    log.error("Syntax not found : 'http' or 'https'.");
                }
                if(feed != null) {
                    feed.getMessages().stream()
                        .map(feedBoardFactory::getFeedBoard)
                        .filter(Objects::nonNull)
                        .forEach(feedBoard->feedBoards.add(feedBoard));
                }
            }
            feedBoardRepository.saveAll(feedBoards);
            for(FeedBoard feedBoard : feedBoards) {
                log.info("[NEW FEED] {}", feedBoard.toString());
            }
        }
        else {
            log.error("Urls is null. please check JsonReader !");
        }
    }
}
