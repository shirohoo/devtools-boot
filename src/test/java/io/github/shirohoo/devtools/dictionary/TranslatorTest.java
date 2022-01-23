package io.github.shirohoo.devtools.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TranslatorTest {
    @Autowired
    Translator translator;

    @Test
    @DisplayName("영단어를 한글 단어로 번역한다")
    void translate() {
        // ...given
        String enWord = "overview";

        // ...when
        String krWord = translator.translate(enWord)
            .orElseThrow(() -> new NoSuchElementException("Not found word."));

        // ...then
        assertThat(krWord).isNotNull();
        assertThat(krWord).isNotEmpty();
        assertThat(krWord).isNotBlank();
        assertThat(krWord).containsPattern("[가-힣]");
    }
}
