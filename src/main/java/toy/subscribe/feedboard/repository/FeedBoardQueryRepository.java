package toy.subscribe.feedboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.feedboard.dto.FeedBoardDto;

public interface FeedBoardQueryRepository {
    Page<FeedBoardDto> findPage(Pageable pageable, String company, String title);
}
