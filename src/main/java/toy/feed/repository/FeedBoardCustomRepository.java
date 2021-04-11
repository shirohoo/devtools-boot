package toy.feed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.feed.domain.dto.FeedBoardDto;

public interface FeedBoardCustomRepository {
    
    Page<FeedBoardDto> getPageFeedBoard (Pageable pageable, String company, String title);
    
}
