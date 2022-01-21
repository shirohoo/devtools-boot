package io.github.shirohoo.devtools.bookmark;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks/categories")
class BookmarkCategoryApi {
    private final BookmarkCategoryRepository bookmarkCategoryRepository;

    @GetMapping
    List<String> findCategories() {
        return bookmarkCategoryRepository.findAll()
            .stream()
            .map(BookmarkCategory::getCategory)
            .collect(Collectors.toUnmodifiableList());
    }
}
