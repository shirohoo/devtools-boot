package toy.subscribe.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.dtos.FeedBoardResponse;

public interface FeedBoardQueryRepository {
    Page<FeedBoardResponse> findPage(Pageable pageable, String company, String title);
}
