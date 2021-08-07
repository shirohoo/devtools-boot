package toy.subscribe.bookmark.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.bookmark.dto.BookmarkDto;
import toy.subscribe.configs.model.BaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {
    private String category;
    private String title;
    private String link;

    @Builder(access = AccessLevel.PUBLIC)
    private Bookmark(final Long id, final String category, final String title, final String link) {
        super.id = id;
        this.category = category;
        this.title = title;
        this.link = link;
    }

    public BookmarkDto convert() {
        return BookmarkDto.builder()
                          .id(super.getId())
                          .category(this.category)
                          .title(this.title)
                          .link(this.link)
                          .build();
    }
}
