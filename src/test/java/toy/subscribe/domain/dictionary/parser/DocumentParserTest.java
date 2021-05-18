package toy.subscribe.domain.dictionary.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentParserTest {
    
    @ParameterizedTest
    @DisplayName("Document_파싱")
    @MethodSource("whereDocuments")
    public void parsingDocumentationFromHTMLFile(String path) throws Exception {
        // when
        DocumentParser parser = new DocumentParser();
        String html = parser.read(path);
        Set<String> set = parser.parsing(html);
    
        System.out.println(set.size());
        StringBuilder sb = new StringBuilder();
        for(String s : set) {
            sb.append(s + " ");
        }
        System.out.println(sb);
    
        // then
        assertThat(set).isNotNull();
    }
    
    // given
    private static Stream<Arguments> whereDocuments() {
        return Stream.of(
                Arguments.of("/models/springboot_document.html"),
                Arguments.of("/models/springsecurity_document.html")
                        );
    }
    
}
