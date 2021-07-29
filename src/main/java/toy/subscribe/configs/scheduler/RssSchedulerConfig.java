package toy.subscribe.configs.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.board.service.CollectPostService;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RssSchedulerConfig {
    private static final int COLLECT_DELAY = 1000 * 60 * 1; // 단위: ms

    private final CollectPostService collectPostService;

    @Transactional
    @Scheduled(fixedRate = COLLECT_DELAY)
    public void collect() {
        collectPostService.allGroupFeedCollect();
    }
}
