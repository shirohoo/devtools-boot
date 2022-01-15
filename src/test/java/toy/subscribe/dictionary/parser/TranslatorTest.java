package toy.subscribe.dictionary.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import toy.subscribe.config.external.ApiProperties;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "카카오 API 호출 횟수 제한으로 테스트 비활성화")
@SpringBootTest(classes = {ApiProperties.class, Translator.class, ObjectMapper.class})
class TranslatorTest {
    private final Translator translator;

    public TranslatorTest(final Translator translator) {
        this.translator = translator;
    }

    @Test
    @DisplayName("영단어를 한글 단어로 번역한다")
    void translate() {
        // given
        String enWord = "overview";

        // when
        Optional<String> translate = translator.translate(enWord);
        String krWord = translate.orElseThrow(() -> new NoSuchElementException("단어를 찾지 못했습니다."));

        // then
        assertThat(krWord).isNotNull().isNotEmpty().isNotBlank();
    }
}
