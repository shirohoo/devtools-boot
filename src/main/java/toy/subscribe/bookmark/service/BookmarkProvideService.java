package toy.subscribe.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.bookmark.dto.BookmarkDto;
import toy.subscribe.bookmark.repository.BookmarkRepository;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;
import toy.subscribe.configs.http.wrapper.HttpResponseWrapper;

@Service
@RequiredArgsConstructor
public class BookmarkProvideService {
    private final BookmarkRepository bookmarkRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    public HttpResponseWrapper<BookmarkDto> provideBookmarkWrapper(Pageable pageable, String category, String title) {
        return HttpResponseWrapper.<BookmarkDto>builder()
                                  .pages(bookmarkRepository.findPage(pageable, category, title))
                                  .visitorsOfDay(requestLogRepository.findDau())
                                  .visitorsOfReduce(requestLogRepository.findCumulativeVisitors())
                                  .build();
    }
}
