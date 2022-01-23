package io.github.shirohoo.devtools.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HtmlParserTest {
    @MethodSource
    @ParameterizedTest
    @DisplayName("Document 파싱")
    public void parsingDocumentationFromHTMLFile(String htmlUrl) {
        HtmlParser parser = HtmlParser.getInstance();
        String html = parser.read(htmlUrl);
        Set<String> words = parser.parse(html);

        System.out.println("words = " + words.size());
        System.out.println(String.join(" ", words));
        assertThat(parser.parse(html)).isNotNull();
    }

    private static Stream<Arguments> parsingDocumentationFromHTMLFile() {
        return Stream.of(
            Arguments.of("https://junit.org/junit5/docs/current/user-guide/"),
            Arguments.of("https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html"),
            Arguments.of("https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference"),
            Arguments.of("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"),
            Arguments.of("https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/htmlsingle/")
        );
    }
}
