package toy.subscribe.common.config.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
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