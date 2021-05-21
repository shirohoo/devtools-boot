package toy.subscribe.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.subscribe.domain.board.model.FeedBoard;

public interface FeedBoardRepository extends JpaRepository<FeedBoard, Long>,
                                             QuerydslPredicateExecutor<FeedBoard>,
                                             FeedBoardCustomRepository {
    
    boolean existsByGuid(String guid);
    
}
