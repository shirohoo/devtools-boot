package toy.subscribe.bookmark;

import lombok.Builder;
import lombok.Getter;

@Getter
class BookmarkDto {
    private Long id;
    private String category;
    private String title;
    private String link;

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
