package toy.subscribe.domain.bookmark.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.bookmark.model.BookmarkResponseWrapper;

public interface BookmarkProvideService {
    BookmarkResponseWrapper provideBookmarkWrapper(Pageable pageable, String category, String title);
}
