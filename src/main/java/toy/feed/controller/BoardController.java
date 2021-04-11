package toy.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import toy.feed.repository.FeedBoardRepository;

@Controller
@RequiredArgsConstructor
public class BoardController {
    
    private final FeedBoardRepository feedBoardRepository;
    
    @GetMapping("/")
    public String index () throws Exception {
        return "board";
    }
    
}
