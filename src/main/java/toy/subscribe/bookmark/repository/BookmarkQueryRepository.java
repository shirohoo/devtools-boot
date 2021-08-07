package toy.subscribe.bookmark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.bookmark.dto.BookmarkDto;

public interface BookmarkQueryRepository {
    Page<BookmarkDto> findPage(Pageable pageable, String category, String title);
}