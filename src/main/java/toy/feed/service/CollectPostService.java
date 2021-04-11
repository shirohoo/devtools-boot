package toy.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.feed.domain.FeedBoard;
import toy.feed.object.RSSFeed;
import toy.feed.object.RSSFeedMessage;
import toy.feed.object.RSSFeedParser;
import toy.feed.repository.FeedBoardRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectPostService {
    
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final FeedBoardRepository feedBoardRepository;
    private final FeedBoardFactory feedBoardFactory;
    
    public void getAllGroupFeed () throws Exception {
        loopCrawl();
    }
    
    private void loopCrawl () throws Exception{
        List<String> urls = new ArrayList<>(Arrays.asList("https://woowabros.github.io/feed.xml",
                                                          "https://medium.com/feed/watcha",
                                                          "https://blog.toss.im/feed/",
                                                          "https://dailyhotel.io/feed",
                                                          "https://medium.com/feed/daangn",
                                                          "https://tech.kakao.com/feed/",
                                                          "https://yanolja.github.io/feed.xml",
                                                          "https://yanolja.github.io/feed.xml",
                                                          "https://engineering.linecorp.com/ko/feed/",
                                                          "https://helloworld.kurly.com/feed.xml"
                                                         ));
        
        for (String url : urls) {
            RSSFeedParser parser = new RSSFeedParser(url);
            RSSFeed feed = parser.readFeed();
            
            for (RSSFeedMessage message : feed.getMessages()) {
                FeedBoard feedBoard = feedBoardFactory.getFeedBoardFrom(message);
                if (feedBoard != null) {
                    log.info("[LOGGING : "
                             + timeFormatter.format(LocalDateTime.now())
                             + " ] <SAVE> "
                             + feedBoard.getCompany()
                             + " : "
                             + feedBoard.getTitle());
                    
                    feedBoardRepository.save(feedBoard);
                }
            }
        }
    }
    
}
