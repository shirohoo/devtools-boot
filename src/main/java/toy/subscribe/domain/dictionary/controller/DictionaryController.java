package toy.subscribe.domain.dictionary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DictionaryController {
    
    @GetMapping("/dictionary")
    public String dictionary() {
        return "dictionary";
    }
    
}
