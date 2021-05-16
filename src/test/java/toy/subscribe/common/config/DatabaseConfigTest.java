package toy.subscribe.common.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import toy.subscribe.common.config.properties.DatabaseProperties;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DatabaseProperties.class, DatabaseConfig.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DatabaseConfigTest {
    
    private final DatabaseProperties databaseProperties;
    private final DatabaseConfig databaseConfig;
    
    // given
    DatabaseConfigTest(DatabaseProperties databaseProperties,
                       DatabaseConfig databaseConfig) {
        this.databaseProperties = databaseProperties;
        this.databaseConfig = databaseConfig;
    }
    
    @Test
    @DisplayName("프로퍼티를_읽어_데이터소스를_생성한다")
    void createDataSourceByProperties() throws SQLException {
        // when
        DataSource dataSource = databaseConfig.createDataSourceByProperties();
        
        // then
        assertThat(dataSource).isNotNull();
        assertThat(dataSource.getConnection()).isNotNull();
    }
    
}