package toy.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.feed.domain.FeedBoard;
import toy.feed.domain.dto.FeedBoardDto;
import toy.feed.repository.FeedBoardRepository;
import toy.feed.service.CollectPostService;

@Controller
@RequiredArgsConstructor
public class MainController {
    
    private final FeedBoardRepository feedBoardRepository;
    private final CollectPostService collectPostService;
    
    @GetMapping ("/")
    public String index () throws Exception {
        return "board";
    }
    
    @ResponseBody
    @GetMapping (value = "/collects")
    public Page<FeedBoardDto> getBoards ( Pageable pageable ) {
        return feedBoardRepository.getPageFeedBoard(pageable)
                                  .map(feedBoard -> toDto(feedBoard));
    }
    
    @ResponseBody
    @GetMapping("/test")
    public void test() throws Exception {
        collectPostService.getAll();
    }
    
    private FeedBoardDto toDto ( FeedBoard board ) {
        return FeedBoardDto.builder()
                           .id(board.getId())
                           .company(board.getCompany())
                           .title(board.getTitle())
                           .link(board.getGuid())
                           .build();
    }
    
}
