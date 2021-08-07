package toy.subscribe.feedboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.feedboard.dto.FeedBoardResponseDto;

public interface FeedBoardQueryRepository {
    Page<FeedBoardResponseDto> findPage(Pageable pageable, String company, String title);
}
