package toy.subscribe.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.bookmark.model.BookmarkResponseWrapper;
import toy.subscribe.domain.bookmark.repository.BookmarkRepository;
import toy.subscribe.domain.logging.repository.RequestLogRepository;

@Service
@RequiredArgsConstructor
public class BookmarkProvideServiceImpl implements BookmarkProvideService {
    private final BookmarkRepository bookmarkRepository;
    private final RequestLogRepository requestLogRepository;
    
    @Override
    @Transactional(readOnly = true)
    public BookmarkResponseWrapper provideBookmarkWrapper(Pageable pageable, String category, String title) {
        return new BookmarkResponseWrapper(bookmarkRepository.getPageFromBookmark(pageable, category, title),
                                           requestLogRepository.getCumulativeVisitors(),
                                           requestLogRepository.getDAU());
    }
}
