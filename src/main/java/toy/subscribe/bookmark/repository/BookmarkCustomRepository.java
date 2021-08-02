package toy.subscribe.bookmark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.dtos.BookmarkDto;

public interface BookmarkCustomRepository {
    Page<BookmarkDto> getPageFromBookmark(Pageable pageable, String category, String title);
}
