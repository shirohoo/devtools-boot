package io.github.shirohoo.devtools.blog;

import io.github.shirohoo.devtools.common.ContentProvider;
import io.github.shirohoo.devtools.config.http.log.repository.HttpLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class BlogPostProvider implements ContentProvider<BlogPost> {
    private final BlogPostRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Override
    public Page<BlogPost> provide(Pageable pageable, String company, String title) {
        return feedBoardRepository.providePages(pageable, company, title);
    }

    @Override
    public Long visitorsOfDay() {
        return requestLogRepository.findDau();
    }

    @Override
    public Long visitorsOfTotal() {
        return requestLogRepository.findCumulativeVisitors();
    }
}
