package toy.subscribe.domain.dictionary.parser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.subscribe.configs.external.ApiProperties;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "API_호출_횟수제한")
@SpringBootTest(classes = ApiProperties.class)
class TranslatorTest {
    @Autowired ApiProperties apiProperties;

    @Test
    @DisplayName("운영서버_테스트이슈_한영단어찾기")
    void translate() {
        // given
        Translator translator = new Translator(apiProperties);
        String enWord = "overview";

        // when
        Optional<String> translate = translator.translate(enWord);
        String s = translate.orElseThrow(()->new NoSuchElementException("단어를 찾지 못했습니다."));

        // then
        assertThat(s).isNotNull()
                     .isNotEmpty()
                     .isNotBlank();

    }
}
