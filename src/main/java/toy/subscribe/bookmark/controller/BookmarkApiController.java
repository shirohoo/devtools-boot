package toy.subscribe.bookmark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.subscribe.bookmark.dto.BookmarkDto;
import toy.subscribe.bookmark.repository.BookmarkRepository;
import toy.subscribe.bookmark.service.BookmarkProvideService;
import toy.subscribe.configs.exception.PageableLargeSizeException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookmarkApiController {
    private final BookmarkProvideService service;
    private final BookmarkRepository repository;

    @GetMapping("/bookmarks")
    public ResponseEntity<?> receiveBookmarksRequest(Pageable pageable,
                                                     @RequestParam(value = "category", required = false) String category,
                                                     @RequestParam(value = "title", required = false) String title) {
        verifyPageable(pageable);
        return new ResponseEntity<>(service.provideBookmarkWrapper(pageable, category, title), HttpStatus.OK);
    }

    private void verifyPageable(final Pageable pageable) {
        if (pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            throw new PageableLargeSizeException();
        }
    }

    @PostMapping("/bookmark")
    public void create(BookmarkDto bookmarkDto) {
        repository.save(bookmarkDto.convert());
    }

    @DeleteMapping("/bookmark/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
