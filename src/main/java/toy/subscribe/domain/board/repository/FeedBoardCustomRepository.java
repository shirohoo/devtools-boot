package toy.subscribe.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.dtos.FeedBoardResponse;

public interface FeedBoardCustomRepository {
    Page<FeedBoardResponse> getPageFromFeedBoard(Pageable pageable, String company, String title);
}
