package toy.subscribe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RSSScheduler {
    
    private final CollectPostService collectPostService;
    
    private final int DELAY = 1000 * 60 * 10; // 단위: ms
    
    @Scheduled(fixedRate = DELAY)
    public void collect() throws Exception {
        log.info("[LOGGING] Scheduler collect RSS !");
        collectPostService.getAllGroupFeed();
    }
    
}
