package toy.feed.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
@RequiredArgsConstructor
public class CollectPostService {
    
    private static final Logger log = LoggerFactory.getLogger(CollectPostService.class);
    
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final FeedBoardRepository feedBoardRepository;
    
    public void getAllGroupFeed () throws Exception {
        loopCrawl();
    }
    
    private void loopCrawl () throws Exception{
        List<String> urls = new ArrayList<>(Arrays.asList("https://woowabros.github.io/feed.xml",
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
                FeedBoard feedBoard = toFeedBoard(message);
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
    
    private FeedBoard toFeedBoard (RSSFeedMessage message) {
        if (isNoDuplicate(message)) {
            
            String url = message.getLink();
            
            if (url.contains("woowabros")) {
                message.setImgPath("/images/woowabros.png");
                message.setCompany("우아한형제들");
            }
            else if (url.contains("toss")) {
                message.setImgPath("/images/toss.png");
                message.setCompany("토스");
            }
            else if (url.contains("dailyhotel")) {
                message.setImgPath("/images/dailyhotel.png");
                message.setCompany("데일리호텔");
            }
            else if (url.contains("daangn")) {
                message.setImgPath("/images/daangn.png");
                message.setCompany("당근마켓");
            }
            else if (url.contains("kakao")) {
                message.setImgPath("/images/kakao.png");
                message.setCompany("카카오");
            }
            else if (url.contains("yanolja")) {
                message.setImgPath("/images/yanolja.png");
                message.setCompany("야놀자");
            }
            else if (url.contains("line")) {
                message.setImgPath("/images/line.png");
                message.setCompany("라인");
            }
            else if (url.contains("thefarmersfront")) {
                message.setImgPath("/images/kurly.png");
                message.setCompany("마켓컬리");
            }
            return FeedBoard.builder()
                            .title(message.getTitle())
                            .company(message.getCompany())
                            .imgPath(message.getImgPath())
                            .guid(message.getGuid())
                            .build();
        }
        return null;
    }
    
    private boolean isNoDuplicate (RSSFeedMessage message) {
        return feedBoardRepository.countByGuid(message.getGuid().trim()) == 0L;
    }
    
}
