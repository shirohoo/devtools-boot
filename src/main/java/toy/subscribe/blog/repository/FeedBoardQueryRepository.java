package toy.subscribe.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.blog.dto.FeedBoardDto;

public interface FeedBoardQueryRepository {
    Page<FeedBoardDto> findPage(Pageable pageable, String company, String title);
}
