package toy.subscribe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toy.subscribe.domain.dto.FeedBoardDto;
import toy.subscribe.repository.FeedBoardRepository;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    
    private final FeedBoardRepository feedBoardRepository;
    
    @GetMapping("/boards")
    public Page<FeedBoardDto> getBoards (Pageable pageable,
                                         @RequestParam(value = "company", required = false) String company,
                                         @RequestParam(value = "title", required = false) String title) {
        
        if(isNull(company)) {
            company = "";
        }
        if(isNull(title)) {
            title = "";
        }
    
        return feedBoardRepository.findPageByFeedBoard(pageable, company, title);
    }
    
    private boolean isNull (String s) {
        return s == null;
    }
    
}
