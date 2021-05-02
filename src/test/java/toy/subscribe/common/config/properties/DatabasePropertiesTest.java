package toy.subscribe.common.config.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource("file:${user.home}/env/db.properties")
class DatabasePropertiesTest {
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Test
    @DisplayName("프로퍼티를_역직렬화_한다")
    public void readProperties() throws Exception {
        assertThat(url).startsWith("jdbc:mysql")
                       .contains("amazonaws")
                       .endsWith("feed")
                       .isNotNull()
                       .isNotEmpty();
        
        assertThat(username).isNotNull()
                            .isNotEmpty();
        
        assertThat(password).isNotNull()
                            .isNotEmpty();
    }
    
}