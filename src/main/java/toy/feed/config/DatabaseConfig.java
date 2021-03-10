package toy.feed.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Autowired
    private DatabaseProperties databaseProperties;
    
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
