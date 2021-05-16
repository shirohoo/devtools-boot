package toy.subscribe.common.config;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.subscribe.common.config.properties.DatabaseProperties;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "운영서버_테스트이슈_DB설정")
@SpringBootTest(classes = {DatabaseProperties.class, DatabaseConfig.class})
class DatabaseConfigTest {
    
    @Autowired
    DatabaseProperties databaseProperties;
    
    @Autowired
    DatabaseConfig databaseConfig;
    
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