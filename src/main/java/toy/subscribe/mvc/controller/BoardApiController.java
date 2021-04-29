package toy.subscribe.mvc.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toy.subscribe.domain.response.FeedBoardResponseDto;
import toy.subscribe.mvc.repository.FeedBoardRepository;
import toy.subscribe.mvc.repository.RequestLogRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {
    
    private final FeedBoardRepository feedBoardRepository;
    private final RequestLogRepository requestLogRepository;
    
    @GetMapping("/boards")
    public ResponseEntity<?> getBoards(Pageable pageable,
                                       @RequestParam(value = "company", required = false) String company,
                                       @RequestParam(value = "title", required = false) String title) {
        
        if(pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            return new ResponseEntity<>("Request page size is too large !", HttpStatus.BAD_REQUEST);
        }

        if(isNull(company)) {
            company = "";
        }
        if(isNull(title)) {
            title = "";
        }
    
        return new ResponseEntity<>(new Result(
                feedBoardRepository.findPageByFeedBoard(pageable, company, title),
                requestLogRepository.findDau(),
                requestLogRepository.findAllVisitors()
        ), HttpStatus.OK);
    }
    
    private boolean isNull(String s) {
        return s == null;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private class Result<T> {
    
        private Page<FeedBoardResponseDto> pages;
        private Long dau;
        private Long allVisitors;
        
    }
    
}
