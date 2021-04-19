package toy.subscribe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.RSSFeed;
import toy.subscribe.factory.FeedBoardFactory;
import toy.subscribe.parser.RSSFeedParser;
import toy.subscribe.repository.FeedBoardRepository;
import toy.subscribe.service.CollectPostService;

import java.util.List;
import java.util.Objects;

import static toy.subscribe.parser.JsonReader.readUrls;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectPostServiceImpl implements CollectPostService {
    
    private final FeedBoardFactory feedBoardFactory;
    private final FeedBoardRepository feedBoardRepository;
    
    @Transactional
    public void getAllGroupFeed() throws Exception {
        loopCrawl();
    }
    
    private void loopCrawl() throws Exception {
        List<String> urls = readUrls();
        
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
