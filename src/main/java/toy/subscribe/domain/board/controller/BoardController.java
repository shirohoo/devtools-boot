package toy.subscribe.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    @GetMapping("/")
    public String index() {
        return "board";
    }
}
