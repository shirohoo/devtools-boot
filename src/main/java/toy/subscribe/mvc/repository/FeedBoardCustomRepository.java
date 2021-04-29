package toy.subscribe.mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.dto.FeedBoardDto;

public interface FeedBoardCustomRepository {
    
    Page<FeedBoardDto> findPageByFeedBoard(Pageable pageable, String company, String title);
    
}
