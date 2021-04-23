package toy.subscribe.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.subscribe.service.CollectPostService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RSSScheduler {
    
    private final CollectPostService collectPostService;
    
    private final int DELAY = 1000 * 60 * 1; // 단위: ms
    
    @Scheduled(fixedRate = DELAY)
    public void collect() throws Exception {
        log.info("[LOGGING] Scheduler collect RSS !");
        collectPostService.getAllGroupFeed();
    }
    
}
