package toy.subscribe.domain.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import toy.subscribe.domain.board.service.FeedBoardProvideService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {
    
    private final FeedBoardProvideService feedBoardProvideService;
    
    @GetMapping("/boards")
    public ResponseEntity<?> receiveFeedBoardsRequest(Pageable pageable,
                                                      @RequestParam(value = "company", required = false) String company,
                                                      @RequestParam(value = "title", required = false) String title) {
        if(pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            return new ResponseEntity<>("Request page size is too large !",
                                        HttpStatus.BAD_REQUEST);
        }
        else {
            if(StringUtils.isEmptyOrWhitespace(company)) {
                company = "";
            }
            if(StringUtils.isEmptyOrWhitespace(title)) {
                title = "";
            }
            return new ResponseEntity<>(feedBoardProvideService.provideFeedBoardWrapper(pageable, company, title), HttpStatus.OK);
        }
    }
    
}
