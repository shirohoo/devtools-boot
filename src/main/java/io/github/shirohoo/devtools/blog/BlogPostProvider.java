package io.github.shirohoo.devtools.blog;

import io.github.shirohoo.devtools.ContentProvider;
import io.github.shirohoo.devtools.config.HttpLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class BlogPostProvider implements ContentProvider<BlogPost> {
    private final HttpLogRepository httpLogRepository;
    private final BlogPostRepository blogPostRepository;

    @Override
    public Page<BlogPost> provide(Pageable pageable, String company, String title) {
        return blogPostRepository.findPages(pageable, company, title);
    }

    @Override
    public Long visitorsOfDay() {
        return httpLogRepository.findVisitorsOfDay();
    }

    @Override
    public Long visitorsOfTotal() {
        return httpLogRepository.findVisitorsOfTotal();
    }
}
