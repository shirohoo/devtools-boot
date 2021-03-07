package toy.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import toy.feed.service.ManualInput;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class Application {
    
    public static void main ( String[] args ) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public TaskScheduler scheduler () {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        return scheduler;
    }
}
