package toy.feed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource (value = "file:${user.home}/env/db.properties")
public class DatabaseProperties {
    
    @Value ("${spring.datasource.url}")
    private String url;
    
    @Value ("${spring.datasource.username}")
    private String username;
    
    @Value ("${spring.datasource.password}")
    private String password;
    
    public String getUrl () {
        return url;
    }
    
    public String getUsername () {
        return username;
    }
    
    public String getPassword () {
        return password;
    }
    
}
