package io.github.shirohoo.devtools.dictionary;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Disabled("카카오사전 API 일일호출제한 약2,000회")
class DictionaryParserTest {
    private final DictionaryParser dictionaryParser;

    public DictionaryParserTest(DictionaryParser dictionaryParser) {
        this.dictionaryParser = dictionaryParser;
    }

    @Test
    @Rollback(value = false)
    @DisplayName(value = "단어장생성기 실행. 테스트가 아닌 메뉴얼 단어장 생성기이다.")
    void create() {
        String html = HtmlPath.SPRING_DATA_JPA.getPath();
        dictionaryParser.parse(html);
    }
}
