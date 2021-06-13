package toy.subscribe.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.common.logging.repository.RequestLogRepository;
import toy.subscribe.domain.bookmark.repository.BookmarkRepository;
import toy.subscribe.common.dtos.ResponseWrapper;

@Service
@RequiredArgsConstructor
public class BookmarkProvideServiceImpl implements BookmarkProvideService {
    private final BookmarkRepository bookmarkRepository;
    private final RequestLogRepository requestLogRepository;
    
    @Override
    @Transactional(readOnly = true)
    public ResponseWrapper provideBookmarkWrapper(Pageable pageable, String category, String title) {
        return new ResponseWrapper(bookmarkRepository.getPageFromBookmark(pageable, category, title),
                                   requestLogRepository.getCumulativeVisitors(),
                                   requestLogRepository.getDAU());
    }
}
