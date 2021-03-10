package toy.feed.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 테스트가 실패하는 경우는 사이트의 RSS 피드 주소가 변하는 경우로 예상 됨
 */
@DisplayName("READ_RSS_TEST")
class RSSFeedParserTest {
    
    @Test
    @DisplayName ("READ_RSS_우아한형제들")
    void woowabros () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://woowabros.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_토스")
    void toss () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://blog.toss.im/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_데일리호텔")
    void dailyHotel () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://dailyhotel.io/feed"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_당근마켓")
    void daangnMarket () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://medium.com/feed/daangn"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_카카오")
    void kakao () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://tech.kakao.com/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_야놀자")
    void yanolja () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://yanolja.github.io/feed.xml"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_라인")
    void line () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://engineering.linecorp.com/ko/feed/"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
    @Test
    @DisplayName ("READ_RSS_마켓컬리")
    void kurly () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://helloworld.kurly.com/feed.xml"
        );
    
        RSSFeed feed = parser.readFeed();
    
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("http://");
            assertThat(message.getGuid().trim()).startsWith("http://");
        }
    
    }
    
    @Test
    @DisplayName ("READ_RSS_왓챠")
    void watcha () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://medium.com/feed/watcha"
        );
        
        RSSFeed feed = parser.readFeed();
        
        for (RSSFeedMessage message : feed.getMessages()) {
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        }
        
    }
    
}
