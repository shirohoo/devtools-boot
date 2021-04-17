package toy.subscribe.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.domain.RSSFeed;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 테스트가 실패하는 경우는 사이트의 RSS 피드 주소가 변하는 경우로 예상 됨
 */
class RSSFeedParserTest {
    
    @Test
    @DisplayName("우아한형제들_RSS_읽기")
    void woowabros () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://woowabros.github.io/feed.xml"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("토스_RSS_읽기")
    void toss () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://blog.toss.im/feed/"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("데일리호텔_RSS_읽기")
    void dailyHotel () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://dailyhotel.io/feed"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("당근마켓_RSS_읽기")
    void daangnMarket () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://medium.com/feed/daangn"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("카카오_RSS_읽기")
    void kakao () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://tech.kakao.com/feed/"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("야놀자_RSS_읽기")
    void yanolja () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://yanolja.github.io/feed.xml"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("라인_RSS_읽기")
    void line () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://engineering.linecorp.com/ko/feed/"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
    @Test
    @DisplayName("마켓컬리_RSS_읽기")
    void kurly () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://helloworld.kurly.com/feed.xml"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("http://");
            assertThat(message.getGuid().trim()).startsWith("http://");
        });
    
    }
    
    @Test
    @DisplayName("왓챠_RSS_읽기")
    void watcha () throws Exception {
        RSSFeedParser parser = new RSSFeedParser(
                "https://medium.com/feed/watcha"
        );
    
        RSSFeed feed = parser.readFeed();
    
        feed.getMessages().forEach(message->{
            assertThat(message.getLink().trim()).startsWith("https://");
            assertThat(message.getGuid().trim()).startsWith("https://");
        });
    
    }
    
}
