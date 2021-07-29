package toy.subscribe.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.configs.dtos.ResponseWrapper;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;
import toy.subscribe.domain.bookmark.repository.BookmarkRepository;

@Service
@RequiredArgsConstructor
public class BookmarkProvideServiceImpl implements BookmarkProvideService {
    private final BookmarkRepository bookmarkRepository;
    private final HttpLogRepository requestLogRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseWrapper provideBookmarkWrapper(Pageable pageable, String category, String title) {
        return new ResponseWrapper(bookmarkRepository.getPageFromBookmark(pageable, category, title),
                                   requestLogRepository.findCumulativeVisitors(),
                                   requestLogRepository.findDau());
    }
}
