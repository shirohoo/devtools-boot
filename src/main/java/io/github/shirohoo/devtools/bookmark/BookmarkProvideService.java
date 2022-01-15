package io.github.shirohoo.devtools.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.shirohoo.devtools.config.http.log.repository.HttpLogRepository;
import io.github.shirohoo.devtools.common.DevtoolsResponse;

@Service
@RequiredArgsConstructor
class BookmarkProvideService {
    private final BookmarkRepository bookmarkRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    DevtoolsResponse<BookmarkDto> provideBookmarkWrapper(Pageable pageable, String category, String title) {
        return DevtoolsResponse.<BookmarkDto>builder()
            .pages(bookmarkRepository.findPage(pageable, category, title))
            .visitorsOfDay(requestLogRepository.findDau())
            .visitorsOfTotal(requestLogRepository.findCumulativeVisitors())
            .build();
    }
}
