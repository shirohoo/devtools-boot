package toy.subscribe.domain.bookmark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.subscribe.common.dtos.BookmarkDto;
import toy.subscribe.domain.bookmark.repository.BookmarkRepository;
import toy.subscribe.domain.bookmark.service.BookmarkProvideService;

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
        if(pageable.getPageSize() > 200) {
            log.error("Request page size is too large !");
            return new ResponseEntity<>("Request page size is too large !", HttpStatus.BAD_REQUEST);
        }
        else {
            if(StringUtils.isEmpty(category)) {
                category = "";
            }
            if(StringUtils.isEmpty(title)) {
                title = "";
            }
            return new ResponseEntity<>(service.provideBookmarkWrapper(pageable, category, title), HttpStatus.OK);
        }
    }
    
    @PostMapping("/bookmark")
    public void create(BookmarkDto bookmarkDTO) {
        repository.save(bookmarkDTO.toEntity());
    }
    
    @DeleteMapping("/bookmark/{id}")
    public void delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
