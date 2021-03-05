package toy.feed.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.feed.object.*;
import toy.feed.repository.FeedBoardRepository;

@SpringBootTest
@Transactional
class FeedBoardTest {
    
    @Autowired FeedBoardRepository feedBoardRepository;
    
    @Test
    @DisplayName ("저장_우아한형제들")
    void woowabros () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://woowabros.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("toFeedBoard(message) = " + toFeedBoard(message));
            feedBoardRepository.save(toFeedBoard(message));
        }
        
    }
    
    @Test
    @DisplayName ("저장_토스")
    void toss () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://blog.toss.im/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
    
    @Test
    @DisplayName ("저장_데일리호텔")
    void dailyHotel () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://dailyhotel.io/feed"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
    
    @Test
    @DisplayName ("저장_당근마켓")
    void daangnMarket () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://medium.com/feed/daangn"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
    
    @Test
    @DisplayName ("저장_카카오")
    void kakao () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://tech.kakao.com/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
    
    @Test
    @DisplayName ("저장_야놀자")
    void yanolja () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://yanolja.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
    
    private FeedBoard toFeedBoard ( RSSFeedMessage message ) {
        String url = message.getLink();
        
        if (url.contains("woowabros")) {
            message.setDescription("우아한형제들");
        }
        else if (url.contains("toss")) {
            message.setDescription("토스");
        }
        else if (url.contains("dailyhotel")) {
            message.setDescription("데일리호텔");
        }
        else if (url.contains("daangn")) {
            message.setDescription("당근마켓");
        }
        else if (url.contains("kakao")) {
            message.setDescription("카카오");
        }
        else if (url.contains("yanolja")) {
            message.setDescription("야놀자");
        }
        
        return FeedBoard.builder()
                        .title(message.getTitle())
                        .company(message.getDescription())
                        .guid(message.getGuid())
                        .build();
    }
    
}
