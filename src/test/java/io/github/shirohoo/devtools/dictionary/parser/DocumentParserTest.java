package io.github.shirohoo.devtools.dictionary.parser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.shirohoo.devtools.dictionary.type.HtmlPath;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "파싱테스트 패스 실패하는 경우 HTML 경로 잘못된 경우")
public class DocumentParserTest {
    @MethodSource
    @ParameterizedTest
    @DisplayName("Document 파싱")
    public void parsingDocumentationFromHTMLFile(String path) {
        // when
        DocumentParser parser = DocumentParser.getInstance();
        String html = parser.read(path);
        Set<String> set = parser.filtering(parser.parsing(html));

        // ##############################################################################
        // ################################ 출 력 로 직 #################################
        // ##############################################################################
        System.out.println(set.size());
        StringBuilder stringBuilder = new StringBuilder();
        set.stream().map(s -> s + " ").forEach(stringBuilder::append);
        System.out.println(stringBuilder);
        // ##############################################################################
        // ##############################################################################
        // ##############################################################################

        // then
        assertThat(parser.parsing(html)).isNotNull();
    }

    // given
    private static Stream<Arguments> parsingDocumentationFromHTMLFile() {
        return Stream.of(Arguments.of(HtmlPath.JUNIT5.getPath()),
                         Arguments.of(HtmlPath.HIBERNATE.getPath()),
                         Arguments.of(HtmlPath.SPRING_DATA_JPA.getPath()),
                         Arguments.of(HtmlPath.SPRING_BOOT.getPath()),
                         Arguments.of(HtmlPath.SPRING_SECURITY.getPath()));
    }
}
