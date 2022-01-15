package io.github.shirohoo.devtools.bookmark;

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
class BookmarkProvider implements ContentProvider<Bookmark> {
    private final HttpLogRepository httpLogRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public Page<Bookmark> provide(Pageable pageable, String category, String title) {
        return bookmarkRepository.findPages(pageable, category, title);
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
