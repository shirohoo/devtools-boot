package io.github.shirohoo.devtools.blog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class BlogPostApi {
    private final BlogPostProvideService blogPostProvideService;

    @GetMapping("/blogs")
    ResponseEntity<?> receiveFeedBoardsRequest(Pageable pageable,
        @RequestParam(value = "company", required = false) String company,
        @RequestParam(value = "title", required = false) String title) {
        return new ResponseEntity<>(blogPostProvideService.provideFeedBoardWrapper(pageable, company, title), HttpStatus.OK);
    }
}
