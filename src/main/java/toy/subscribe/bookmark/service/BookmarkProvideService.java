package toy.subscribe.bookmark.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.dtos.ResponseWrapper;

public interface BookmarkProvideService {
    ResponseWrapper provideBookmarkWrapper(Pageable pageable, String category, String title);
}
