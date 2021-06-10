package toy.subscribe.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.board.dto.FeedBoardResponse;

public interface FeedBoardCustomRepository {
    Page<FeedBoardResponse> getPageByFeedBoard(Pageable pageable, String company, String title);
}
