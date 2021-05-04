package toy.subscribe.domain.board.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.board.dto.FeedBoardResponseWrapper;

public interface FeedBoardProvideService {
    
    FeedBoardResponseWrapper provideFeedBoardWrapper(Pageable pageable, String company, String title);
    
}
