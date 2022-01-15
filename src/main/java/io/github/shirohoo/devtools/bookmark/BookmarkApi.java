package io.github.shirohoo.devtools.bookmark;

import io.github.shirohoo.devtools.common.ContentProvider;
import io.github.shirohoo.devtools.common.DevtoolsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
class BookmarkApi {
    private final BookmarkRepository bookmarkRepository;
    private final ContentProvider<Bookmark> bookmarkProvider;

    @GetMapping
    ResponseEntity<DevtoolsResponse<BookmarkDto>> findPages(
        Pageable pageable,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "title", required = false) String title
    ) {
        return new ResponseEntity<>(
            DevtoolsResponse.<BookmarkDto>builder()
                .pages(bookmarkProvider.provide(pageable, category, title).map(Bookmark::toDto))
                .visitorsOfDay(bookmarkProvider.visitorsOfDay())
                .visitorsOfTotal(bookmarkProvider.visitorsOfTotal())
                .build(),
            HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    void newBookMark(BookmarkDto bookmarkDto) {
        bookmarkRepository.save(bookmarkDto.toEntity());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    void deleteBookMark(@PathVariable Long id) {
        bookmarkRepository.deleteById(id);
    }
}
