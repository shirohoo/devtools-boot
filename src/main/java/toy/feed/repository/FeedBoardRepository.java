package toy.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.feed.domain.FeedBoard;

public interface FeedBoardRepository extends JpaRepository<FeedBoard, Long>, QuerydslPredicateExecutor<FeedBoard>, FeedBoardCustomRepository {
    
    Long countByGuid (String guid);
    
}
