package toy.feed.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource (value = "file:${user.home}/env/db.properties")
public class DatabaseProperties {
    
    @Value ("${spring.datasource.url}")
    private String url;
    
    @Value ("${spring.datasource.username}")
    private String username;
    
    @Value ("${spring.datasource.password}")
    private String password;
    
}
