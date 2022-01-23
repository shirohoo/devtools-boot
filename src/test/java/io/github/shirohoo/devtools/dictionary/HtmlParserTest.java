package io.github.shirohoo.devtools.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HtmlParserTest {
    @MethodSource
    @ParameterizedTest
    @DisplayName("Document 파싱")
    public void parsingDocumentationFromHtml(String htmlUrl) {
        // ...given
        HtmlParser parser = HtmlParser.getInstance();
        String html = parser.read(htmlUrl);

        // ...when
        Set<String> words = parser.parse(html);

        // ...then
        System.out.println("words = " + words.size());
        System.out.println(String.join(" ", words));
        assertThat(parser.parse(html)).isNotNull();
    }

    private static Stream<Arguments> parsingDocumentationFromHtml() {
        return Stream.of(
            Arguments.of(DocsUrls.JUNIT5.getDocsUrl()),
            Arguments.of(DocsUrls.HIBERNATE.getDocsUrl()),
            Arguments.of(DocsUrls.SPRING_DATA_JPA.getDocsUrl()),
            Arguments.of(DocsUrls.SPRING_BOOT.getDocsUrl()),
            Arguments.of(DocsUrls.SPRING_SECURITY.getDocsUrl())
        );
    }
}
