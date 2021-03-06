package toy.feed.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class RSSScheduler {
    
    private static final Logger log = LoggerFactory.getLogger(RSSScheduler.class);
    
    private final int DELAY = 1000 * 60 * 10;
    
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final CollectPostService collectPostService;
    
    // 단위: ms
    @Scheduled (fixedRate = DELAY)
    public void collect () throws Exception {
        if(log.isInfoEnabled()) {
            log.info("[LOGGING : "
                     + timeFormatter.format(LocalDateTime.now())
                     + "] RSSScheduler.collectPostService.getAllGroupFeed() execute !");
        }
            collectPostService.getAllGroupFeed();
    }
    
}
