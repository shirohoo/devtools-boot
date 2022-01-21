package io.github.shirohoo.devtools.bookmark;

import io.github.shirohoo.devtools.config.BaseEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class BookmarkCategory extends BaseEntity {
    private String category;

    private BookmarkCategory(Long id, String category) {
        super.id = id;
        this.category = category;
    }

    static BookmarkCategory of(String category) {
        return new BookmarkCategory(null, category);
    }
}
