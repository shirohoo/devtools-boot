package io.github.shirohoo.devtools.config.config.properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.github.shirohoo.devtools.config.external.ApiProperties;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "운영서버 API프로퍼티 테스트이슈로 비활성화")
@SpringBootTest(classes = ApiProperties.class)
class ApiPropertiesTest {
    private final ApiProperties apiProperties;

    public ApiPropertiesTest(final ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    @Test
    @DisplayName("프로퍼티를 역직렬화 한다")
    public void readProperties() throws Exception {
        String kakaoKey = apiProperties.getKakaoKey();
        assertThat(kakaoKey).isNotNull()
                            .isNotEmpty()
                            .isNotBlank()
                            .startsWith("KakaoAK");
    }
}
