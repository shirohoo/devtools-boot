package toy.subscribe.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.bookmark.model.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkQueryRepository {
}
