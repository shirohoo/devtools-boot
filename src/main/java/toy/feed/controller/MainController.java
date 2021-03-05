package toy.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.feed.domain.FeedBoard;
import toy.feed.domain.dto.FeedBoardDto;
import toy.feed.repository.FeedBoardRepository;

@Controller
@RequiredArgsConstructor
public class MainController {
    
    private final FeedBoardRepository feedBoardRepository;
    
    @GetMapping ("/")
    public String index () throws Exception {
        return "board";
    }
    
    @ResponseBody
    @GetMapping ("/boards")
    public Page<FeedBoardDto> getBoards (Pageable pageable,
                                         @RequestParam (value = "company", required = false) String company,
                                         @RequestParam (value = "title", required = false) String title) {
        
        if (isNull(company)) {
            company = "";
        }
        if (isNull(title)) {
            title = "";
        }
        
        return feedBoardRepository.getPageFeedBoard(pageable, company, title)
                                  .map(feedBoard -> toDto(feedBoard));
    }
    
    private FeedBoardDto toDto (FeedBoard board) {
        return FeedBoardDto.builder()
                           .id(board.getId())
                           .company(board.getCompany())
                           .imgPath(board.getImgPath())
                           .title(board.getTitle())
                           .link(board.getGuid())
                           .regDate(board.getRegDate())
                           .build();
    }
    
    private boolean isNull (String string) {
        return string == null;
    }
    
}
