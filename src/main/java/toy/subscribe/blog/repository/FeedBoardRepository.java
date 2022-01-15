package toy.subscribe.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.blog.model.FeedBoard;

public interface FeedBoardRepository extends JpaRepository<FeedBoard, Long>, FeedBoardQueryRepository {
    boolean existsByGuid(final String guid);
}
