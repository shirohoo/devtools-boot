package io.github.shirohoo.devtools.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public Executor scheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(new SchedulerThreadFactory());
    }

    private static class SchedulerThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Scheduler");
        }
    }
}
