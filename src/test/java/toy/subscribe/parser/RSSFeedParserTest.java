package toy.subscribe.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import toy.subscribe.domain.RSSFeed;

/**
 * 테스트가 실패하는 경우는 사이트의 RSS 피드 주소가 변하는 경우로 예상 됨
 */
class RSSFeedParserTest {

    @ParameterizedTest
    @DisplayName("RSS_읽기")
    @MethodSource("provideFeedUrlAndKeyword")
    public void readAllGroup(String url, String keyword) throws Exception {
        RSSFeedParser parser = new RSSFeedParser(url);
        RSSFeed feed = parser.readFeed();

        feed.getMessages().forEach(message -> {
            assertThat(message.getLink().trim()).contains(keyword);
            assertThat(message.getLink().trim()).startsWith("http");
            assertThat(message.getGuid().trim()).startsWith("http");
        });
    }

    private static Stream<Arguments> provideFeedUrlAndKeyword() {
        return Stream.of(
                Arguments.of("https://woowabros.github.io/feed.xml", "woowabros"),
                Arguments.of("https://medium.com/feed/watcha", "watcha"),
                Arguments.of("https://blog.toss.im/feed/", "toss"),
                Arguments.of("https://dailyhotel.io/feed", "dailyhotel"),
                Arguments.of("https://medium.com/feed/daangn", "daangn"),
                Arguments.of("https://tech.kakao.com/feed/", "kakao"),
                Arguments.of("https://yanolja.github.io/feed.xml", "yanolja"),
                Arguments.of("https://engineering.linecorp.com/ko/feed/", "line"),
                Arguments.of("https://helloworld.kurly.com/feed.xml", "thefarmersfront")
        );
    }

}
