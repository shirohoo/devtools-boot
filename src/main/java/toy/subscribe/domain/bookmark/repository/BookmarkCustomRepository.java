package toy.subscribe.domain.bookmark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.bookmark.model.BookmarkDTO;

public interface BookmarkCustomRepository {
    Page<BookmarkDTO> getPageFromBookmark(Pageable pageable, String category, String title);
}
