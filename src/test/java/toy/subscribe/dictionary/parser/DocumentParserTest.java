package toy.subscribe.dictionary.parser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import toy.subscribe.dictionary.type.HtmlPath;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "파싱테스트_패스_실패하는_경우_HTML_경로_잘못된_경우")
public class DocumentParserTest {
    @ParameterizedTest
    @DisplayName("Document_파싱")
    @MethodSource("whereDocuments")
    public void parsingDocumentationFromHTMLFile(String path) {
        // when
        DocumentParser parser = new DocumentParser();
        String html = parser.read(path);
        Set<String> set = parser.filtering(parser.parsing(html));

        // ##############################################################################
        // ################################ 출 력 로 직 #################################
        // ##############################################################################
        System.out.println(set.size());
        StringBuilder sb = new StringBuilder();
        for(String s : set) {
            sb.append(s + " ");
        }
        System.out.println(sb);
        // ##############################################################################
        // ##############################################################################
        // ##############################################################################

        // then
        assertThat(parser.parsing(html)).isNotNull();
    }

    // given
    private static Stream<Arguments> whereDocuments() {
        return Stream.of(Arguments.of(HtmlPath.JUNIT5.getPath()),
                         Arguments.of(HtmlPath.HIBERNATE.getPath()),
                         Arguments.of(HtmlPath.SPRING_DATA_JPA.getPath()),
                         Arguments.of(HtmlPath.SPRING_BOOT.getPath()),
                         Arguments.of(HtmlPath.SPRING_SECURITY.getPath()));
    }
}
