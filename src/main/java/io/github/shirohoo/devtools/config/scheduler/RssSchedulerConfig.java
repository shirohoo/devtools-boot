package io.github.shirohoo.devtools.config.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import io.github.shirohoo.devtools.blog.RssSchedulingService;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RssSchedulerConfig {
    private static final int COLLECT_DELAY_MS = 1000 * 60 * 1;

    private final RssSchedulingService rssSchedulingService;

    @Transactional
    @Scheduled(fixedRate = COLLECT_DELAY_MS)
    public void collect() {
        rssSchedulingService.collect();
    }
}
