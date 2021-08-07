package toy.subscribe.bookmark.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.bookmark.dto.BookmarkDto;
import toy.subscribe.configs.http.wrapper.HttpResponseWrapper;

public interface BookmarkProvideService {
    HttpResponseWrapper<BookmarkDto> provideBookmarkWrapper(Pageable pageable, String category, String title);
}
