package toy.subscribe.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.domain.board.model.FeedBoard;

public interface FeedBoardRepository extends JpaRepository<FeedBoard, Long>, FeedBoardQueryRepository {
    boolean existsByGuid(String guid);
}
