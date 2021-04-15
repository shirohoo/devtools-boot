package toy.subscribe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.RSSFeed;
import toy.subscribe.domain.RSSFeedParser;
import toy.subscribe.repository.FeedBoardRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectPostService {
    
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final FeedBoardRepository feedBoardRepository;
    private final FeedBoardFactory feedBoardFactory;
    
    @Transactional(readOnly = true)
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
    
        for(String url : urls) {
            RSSFeedParser parser = new RSSFeedParser(url);
            RSSFeed feed = parser.readFeed();
        
            feed.getMessages()
                .stream()
                .map(feedBoardFactory::findFeedBoardFrom)
                .filter(Objects::nonNull)
                .forEach(feedBoard->{
                    log.info("[LOGGING : {}] <SAVE> {} : {}", timeFormatter.format(LocalDateTime.now()), feedBoard.getCompany(), feedBoard.getTitle());
                    feedBoardRepository.save(feedBoard);
                });
        }
    }
    
}
