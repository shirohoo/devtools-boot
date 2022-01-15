package toy.subscribe.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.config.http.log.repository.HttpLogRepository;
import toy.subscribe.config.http.wrapper.HttpResponseWrapper;

@Service
@RequiredArgsConstructor
class BookmarkProvideService {
    private final BookmarkRepository bookmarkRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    HttpResponseWrapper<BookmarkDto> provideBookmarkWrapper(Pageable pageable, String category, String title) {
        return HttpResponseWrapper.<BookmarkDto>builder()
            .pages(bookmarkRepository.findPage(pageable, category, title))
            .visitorsOfDay(requestLogRepository.findDau())
            .visitorsOfReduce(requestLogRepository.findCumulativeVisitors())
            .build();
    }
}
