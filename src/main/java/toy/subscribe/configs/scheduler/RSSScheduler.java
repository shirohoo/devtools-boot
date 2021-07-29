package toy.subscribe.configs.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.board.service.CollectPostService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RSSScheduler {
    private final CollectPostService collectPostService;

    private static final int COLLECT_DELAY = 1000 * 60 * 1; // 단위: ms

    @Transactional
    @Scheduled(fixedRate = COLLECT_DELAY)
    public void collect() {
        collectPostService.allGroupFeedCollect();
    }
}
