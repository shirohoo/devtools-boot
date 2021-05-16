package toy.subscribe.common.config.properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "운영서버_테스트이슈_DB프로퍼티")
@SpringBootTest(classes = DatabaseProperties.class)
class DatabasePropertiesTest {
    
    @Autowired
    DatabaseProperties databaseProperties;
    
    @Test
    @DisplayName("프로퍼티를_역직렬화_한다")
    public void readProperties() throws Exception {
        assertThat(databaseProperties.getUrl()).startsWith("jdbc:mysql")
                                               .contains("amazonaws")
                                               .endsWith("feed")
                                               .isNotNull()
                                               .isNotEmpty()
                                               .isNotBlank();
        
        assertThat(databaseProperties.getUsername()).isNotNull()
                                                    .isNotEmpty()
                                                    .isNotBlank();
        
        assertThat(databaseProperties.getPassword()).isNotNull()
                                                    .isNotEmpty()
                                                    .isNotBlank();
    }
    
}