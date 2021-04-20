package toy.subscribe.controller;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toy.subscribe.domain.dto.FeedBoardDto;
import toy.subscribe.repository.FeedBoardRepository;
import toy.subscribe.repository.RequestLogRepository;

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
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("<h1>400_BAD_REQUEST</h1>\n<h2>요청 사이즈가 너무 큽니다</h2>");
        }
    
        if(isNull(company)) {
            company = "";
        }
        if(isNull(title)) {
            title = "";
        }
    
        return new ResponseEntity<>(new Result(feedBoardRepository.findPageByFeedBoard(pageable, company, title),
                                               requestLogRepository.getDau()), HttpStatus.OK);
    }
    
    private boolean isNull(String s) {
        return s == null;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private class Result<T> {
        
        private Page<FeedBoardDto> pages;
        private Long dau;
        
    }
    
}
