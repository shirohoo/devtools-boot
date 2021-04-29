package toy.subscribe.mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.response.FeedBoardResponseDto;

public interface FeedBoardCustomRepository {
    
    Page<FeedBoardResponseDto> findPageByFeedBoard(Pageable pageable, String company, String title);
    
}
