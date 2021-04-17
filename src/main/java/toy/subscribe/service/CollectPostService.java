package toy.subscribe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.RSSFeed;
import toy.subscribe.factory.FeedBoardFactory;
import toy.subscribe.parser.RSSFeedParser;
import toy.subscribe.parser.UrlReader;
import toy.subscribe.repository.FeedBoardRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectPostService {
    
    private final FeedBoardRepository feedBoardRepository;
    private final FeedBoardFactory feedBoardFactory;
    
    @Transactional(readOnly = true)
    public void getAllGroupFeed() throws Exception {
        loopCrawl();
    }
    
    private void loopCrawl() throws Exception {
        UrlReader urlReader = new UrlReader();
        
        List<String> urls = urlReader.read();
        
        for(String url : urls) {
            RSSFeedParser parser = new RSSFeedParser(url);
            RSSFeed feed = parser.readFeed();
            
            feed.getMessages()
                .stream()
                .map(feedBoardFactory::findFeedBoardFrom)
                .filter(Objects::nonNull)
                .forEach(feedBoard->{
                    log.info("[LOGGING] <SAVE> {} : {}", feedBoard.getCompany(), feedBoard.getTitle());
                    feedBoardRepository.save(feedBoard);
                });
        }
    }
    
}
