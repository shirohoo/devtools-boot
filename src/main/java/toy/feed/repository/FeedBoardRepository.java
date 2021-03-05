package toy.feed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.feed.domain.FeedBoard;

public interface FeedBoardRepository extends JpaRepository<FeedBoard, Long> {
    
    Long countByGuid (String guid);
    
    @Query (value = "SELECT F FROM FeedBoard F WHERE F.company LIKE %:company% AND F.title LIKE %:title% ORDER BY F.id DESC",
            countQuery = "SELECT count(F) FROM FeedBoard F WHERE F.company LIKE %:company% AND F.title LIKE %:title%")
    Page<FeedBoard> getPageFeedBoard (Pageable pageable,
                                      @Param ("company") String company,
                                      @Param ("title") String title);
    
}
