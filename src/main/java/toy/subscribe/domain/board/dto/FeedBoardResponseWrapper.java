package toy.subscribe.domain.board.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoardResponseWrapper<T> {
    
    private Page<FeedBoardResponse> pages;
    private Long visitorsOfReduce;
    private Long visitorsOfDay;
    
}