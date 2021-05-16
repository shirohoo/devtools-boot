package toy.subscribe.common.config.properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "운영서버_테스트이슈_API프로퍼티")
@SpringBootTest(classes = ApiProperties.class)
class ApiPropertiesTest {
    
    @Autowired
    ApiProperties apiProperties;
    
    @Test
    @DisplayName("프로퍼티를_역직렬화_한다")
    public void readProperties() throws Exception {
        String kakaoKey = apiProperties.getKakaoKey();
        assertThat(kakaoKey).isNotNull()
                            .isNotEmpty()
                            .isNotBlank()
                            .startsWith("KakaoAK");
    }
    
}