package io.github.shirohoo.devtools.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled(value = "운영서버 API프로퍼티 테스트이슈로 비활성화")
@SpringBootTest(classes = KakaoProperties.class)
class KakaoPropertiesTest {
    private final KakaoProperties kakaoProperties;

    public KakaoPropertiesTest(final KakaoProperties kakaoProperties) {
        this.kakaoProperties = kakaoProperties;
    }

    @Test
    @DisplayName("프로퍼티를 역직렬화 한다")
    public void readProperties() throws Exception {
        String kakaoKey = kakaoProperties.getKakaoKey();
        assertThat(kakaoKey).isNotNull()
            .isNotEmpty()
            .isNotBlank()
            .startsWith("KakaoAK");
    }
}
