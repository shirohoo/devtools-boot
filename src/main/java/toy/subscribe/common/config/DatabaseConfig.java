package toy.subscribe.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import toy.subscribe.common.config.properties.DatabaseProperties;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class DatabaseConfig {
    
    private final DatabaseProperties databaseProperties;
    
    @Bean
    @Primary
    public DataSource customDataSource () {
        return DataSourceBuilder
                .create()
                .url(databaseProperties.getUrl())
                .username(databaseProperties.getUsername())
                .password(databaseProperties.getPassword())
                .build();
    }
    
}
