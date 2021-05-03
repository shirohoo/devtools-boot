package toy.subscribe.domain.board.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import toy.subscribe.domain.board.dto.FeedBoardResponseDto;
import toy.subscribe.domain.board.repository.FeedBoardRepository;
import toy.subscribe.domain.logging.repository.RequestLogRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {
    
    private final FeedBoardRepository feedBoardRepository;
    private final RequestLogRepository requestLogRepository;
    
    @GetMapping("/boards")
    public ResponseEntity<?> requestFeedBoards(Pageable pageable,
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
            return new ResponseEntity<>(new FeedBoardResponseWrapper(feedBoardRepository.findPageByFeedBoard(pageable, company, title),
                                                                     requestLogRepository.findDau(),
                                                                     requestLogRepository.findAllVisitors()),
                                        HttpStatus.OK);
        }
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private class FeedBoardResponseWrapper<T> {
        
        private Page<FeedBoardResponseDto> pages;
        private Long dau;
        private Long allVisitors;
        
    }
    
}
