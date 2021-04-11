package toy.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RSSScheduler {
    
    private final int DELAY = 1000 * 60 * 10; // 단위: ms
    
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final CollectPostService collectPostService;
    
    @Scheduled (fixedRate = DELAY)
    public void collect () throws Exception {
        log.info("[LOGGING : "
                 + timeFormatter.format(LocalDateTime.now())
                 + "] RSSScheduler.collectPostService.getAllGroupFeed() execute !");
        
        collectPostService.getAllGroupFeed();
    }
    
}
