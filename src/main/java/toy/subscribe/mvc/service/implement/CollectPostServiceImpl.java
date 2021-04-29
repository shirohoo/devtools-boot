package toy.subscribe.mvc.service.implement;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.RSSFeed;
import toy.subscribe.factory.FeedBoardFactory;
import toy.subscribe.parser.JsonReader;
import toy.subscribe.parser.RSSFeedParser;
import toy.subscribe.mvc.repository.FeedBoardRepository;
import toy.subscribe.mvc.service.CollectPostService;


@Slf4j
@Service
@RequiredArgsConstructor
public class CollectPostServiceImpl implements CollectPostService {

    private final FeedBoardFactory feedBoardFactory;
    private final FeedBoardRepository feedBoardRepository;

    private List<String> urls;

    @Transactional
    public void getAllGroupFeed() {
        readUrlsFromJsonProperties();
        collectRSS();
    }

    private void readUrlsFromJsonProperties() {
        JsonReader jsonReader = new JsonReader();
        this.urls = jsonReader.readUrls();
    }

    private void collectRSS() {
        RSSFeed feed = null;

        if (urls != null) {
            for (String url : urls) {
                try {
                    RSSFeedParser parser = new RSSFeedParser(url);
                    feed = parser.readFeed();
                } catch (XMLStreamException e) {
                    log.error("XML parsing error.");
                } catch (MalformedURLException e) {
                    log.error("Syntax not found : 'http' or 'https'.");
                }

                if (feed != null) {
                    feed.getMessages()
                            .stream()
                            .map(feedBoardFactory::findFeedBoardFrom)
                            .filter(Objects::nonNull)
                            .forEach(feedBoard -> {
                                log.info("[NEW RSS] {} : {}", feedBoard.getCompany(), feedBoard.getTitle());
                                feedBoardRepository.save(feedBoard);
                            });
                }
            }
        } else {
            log.error("urls is null. please check JsonReader !");
        }
    }

}
