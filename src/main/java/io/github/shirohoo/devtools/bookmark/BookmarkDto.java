package io.github.shirohoo.devtools.bookmark;

import lombok.Builder;
import lombok.Value;

@Value
class BookmarkDto {
    Long id;
    String category;
    String title;
    String link;

    @Builder
    private BookmarkDto(Long id, String category, String title, String link) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.link = link;
    }

    Bookmark toEntity() {
        return Bookmark.builder()
            .id(id)
            .category(category)
            .title(title)
            .link(link)
            .build();
    }
}
