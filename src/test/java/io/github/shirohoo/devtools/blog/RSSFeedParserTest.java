package io.github.shirohoo.devtools.blog;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 테스트가 실패하는 경우는 사이트의 RSS 피드 주소가 변하는 경우로 예상 됨
 */
class RSSFeedParserTest {
    @MethodSource
    @ParameterizedTest
    @DisplayName("전사 RSS 호출 파싱 테스트")
    public void readAllGroup(String url, String keyword) throws Exception {
        RSSFeedParser parser = RSSFeedParser.from(url);
        RSSFeed feed = parser.readFeed();

        feed.getMessages().forEach(message -> {
            assertThat(message.getLink().trim()).contains(keyword);
            assertThat(message.getLink().trim()).startsWith("http");
            assertThat(message.getGuid().trim()).startsWith("http");
        });
    }

    private static Stream<Arguments> readAllGroup() {
        return Stream.of(
            Arguments.of("https://techblog.woowahan.com/feed/", "woowahan"),
            Arguments.of("https://woowabros.github.io/feed.xml", "woowabros"),
            Arguments.of("https://tech.kakao.com/feed/", "kakao"),
            Arguments.of("https://yanolja.github.io/feed.xml", "yanolja"),
            Arguments.of("https://engineering.linecorp.com/ko/feed/", "line"),
            Arguments.of("https://helloworld.kurly.com/feed.xml", "thefarmersfront")
        );
    }
}
