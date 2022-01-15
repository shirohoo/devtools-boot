package toy.subscribe.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkQueryRepository {
}
