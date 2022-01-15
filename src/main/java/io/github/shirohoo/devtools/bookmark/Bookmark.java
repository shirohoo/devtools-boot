package io.github.shirohoo.devtools.bookmark;

import io.github.shirohoo.devtools.config.model.BaseEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
            .id(id)
            .category(category)
            .title(title)
            .link(link)
            .build();
    }
}
