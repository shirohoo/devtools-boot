package toy.subscribe.feedboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.feedboard.model.FeedBoard;

public interface FeedBoardRepository extends JpaRepository<FeedBoard, Long>, FeedBoardQueryRepository {
    boolean existsByGuid(String guid);
}
