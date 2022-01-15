package io.github.shirohoo.devtools.config.config;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.github.shirohoo.devtools.config.database.DatabaseConfig;
import io.github.shirohoo.devtools.config.database.DatabaseProperties;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled(value = "운영서버 DB설정 테스트 이슈로 비활성환")
@SpringBootTest(classes = {DatabaseProperties.class, DatabaseConfig.class})
class DatabaseConfigTest {
    private final DatabaseProperties databaseProperties;
    private final DatabaseConfig databaseConfig;

    public DatabaseConfigTest(final DatabaseProperties databaseProperties, final DatabaseConfig databaseConfig) {
        this.databaseProperties = databaseProperties;
        this.databaseConfig = databaseConfig;
    }

    @Test
    @DisplayName("프로퍼티를 읽어 데이터소스를 생성한다")
    void createDataSourceByProperties() throws SQLException {
        // when
        DataSource dataSource = databaseConfig.createDataSourceByProperties();

        // then
        assertThat(dataSource).isNotNull();
        assertThat(dataSource.getConnection()).isNotNull();
    }
}
