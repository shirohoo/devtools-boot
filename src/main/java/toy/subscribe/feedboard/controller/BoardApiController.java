package toy.subscribe.feedboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toy.subscribe.configs.exception.PageableLargeSizeException;
import toy.subscribe.feedboard.service.FeedBoardProvideService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardApiController {
    private final FeedBoardProvideService feedBoardProvideService;

    @GetMapping("/blogs")
    public ResponseEntity<?> receiveFeedBoardsRequest(Pageable pageable,
                                                      @RequestParam(value = "company", required = false) String company,
                                                      @RequestParam(value = "title", required = false) String title) {
        verifyPageable(pageable);
        return new ResponseEntity<>(feedBoardProvideService.provideFeedBoardWrapper(pageable, company, title), HttpStatus.OK);
    }

    private void verifyPageable(final Pageable pageable) {
        if (pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            throw new PageableLargeSizeException();
        }
    }
}
