package toy.subscribe.bookmark.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.bookmark.model.Bookmark;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDto {
    private Long id;
    private String category;
    private String title;
    private String link;

    @Builder(access = AccessLevel.PUBLIC)
    private BookmarkDto(final Long id, final String category, final String title, final String link) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.link = link;
    }

    public Bookmark convert() {
        return Bookmark.builder()
                       .id(this.id)
                       .category(this.category)
                       .title(this.title)
                       .link(this.link)
                       .build();
    }

}
