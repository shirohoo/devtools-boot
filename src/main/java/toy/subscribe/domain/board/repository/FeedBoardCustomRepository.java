package toy.subscribe.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.board.dto.FeedBoardResponseDto;

public interface FeedBoardCustomRepository {
    
    Page<FeedBoardResponseDto> findPageByFeedBoard(Pageable pageable, String company, String title);
    
}
