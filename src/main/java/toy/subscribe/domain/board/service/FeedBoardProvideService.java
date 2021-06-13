package toy.subscribe.domain.board.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.common.dtos.ResponseWrapper;

public interface FeedBoardProvideService {
    ResponseWrapper provideFeedBoardWrapper(Pageable pageable, String company, String title);
}
