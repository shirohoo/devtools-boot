package io.github.shirohoo.devtools.blog;

import io.github.shirohoo.devtools.ContentProvider;
import io.github.shirohoo.devtools.common.DevtoolsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
class BlogPostApi {
    private final ContentProvider<BlogPost> blogPostProvider;

    @GetMapping
    ResponseEntity<DevtoolsResponse<BlogPostDto>> findPages(
        Pageable pageable,
        @RequestParam(value = "company", required = false) String company,
        @RequestParam(value = "title", required = false) String title
    ) {
        return new ResponseEntity<>(
            DevtoolsResponse.<BlogPostDto>builder()
                .pages(blogPostProvider.provide(pageable, company, title).map(BlogPost::toDto))
                .visitorsOfDay(blogPostProvider.visitorsOfDay())
                .visitorsOfTotal(blogPostProvider.visitorsOfTotal())
                .build(),
            HttpStatus.OK
        );
    }
}
