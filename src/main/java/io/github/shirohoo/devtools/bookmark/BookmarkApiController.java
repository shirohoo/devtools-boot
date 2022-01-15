package io.github.shirohoo.devtools.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
class BookmarkApiController {
    private final BookmarkProvideService service;
    private final BookmarkRepository repository;

    @GetMapping
    ResponseEntity<?> findPage(
        Pageable pageable,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "title", required = false) String title
    ) {
        return new ResponseEntity<>(service.provideBookmarkWrapper(pageable, category, title), HttpStatus.OK);
    }

    @PostMapping
    void newBookMark(BookmarkDto bookmarkDto) {
        repository.save(bookmarkDto.toEntity());
    }

    @DeleteMapping("/{id}")
    void deleteBookMark(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
