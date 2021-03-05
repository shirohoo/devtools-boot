package toy.feed.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RSSFeedParserTest {
    
    @Test
    @DisplayName ("RSS_우아한형제들")
    void woowabros () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://woowabros.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
    
    @Test
    @DisplayName ("RSS_토스")
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
    @DisplayName ("RSS_데일리호텔")
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
    @DisplayName ("RSS_당근마켓")
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
    @DisplayName ("RSS_카카오")
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
    @DisplayName ("RSS_야놀자")
    void yanolja () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://yanolja.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            System.out.println("message = " + message);
        }
        
    }
}
