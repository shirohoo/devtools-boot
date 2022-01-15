package toy.subscribe.bookmark;

import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.config.model.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Bookmark extends BaseEntity {
    private String category;
    private String title;
    private String link;

    @Builder
    private Bookmark(Long id, String category, String title, String link) {
        super.id = id;
        this.category = category;
        this.title = title;
        this.link = link;
    }

    BookmarkDto toDto() {
        return BookmarkDto.builder()
            .id(getId())
            .category(category)
            .title(title)
            .link(link)
            .build();
    }
}
