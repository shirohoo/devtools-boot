package io.github.shirohoo.devtools.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RssFeedScheduler {
    private static final int COLLECT_DELAY_MS = 1000 * 60 * 60;

    private final RssCollector rssCollector;

    @Async("scheduledExecutor")
    @Scheduled(fixedRate = COLLECT_DELAY_MS)
    public void collect() {
        rssCollector.collect();
    }
}
