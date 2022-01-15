package toy.subscribe.config.config.properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import toy.subscribe.config.database.DatabaseProperties;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "운영서버 DB프로퍼티 테스트이슈로 비활성화")
@SpringBootTest(classes = DatabaseProperties.class)
class DatabasePropertiesTest {
    private final DatabaseProperties databaseProperties;

    public DatabasePropertiesTest(final DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Test
    @DisplayName("프로퍼티를 역직렬화 한다")
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
