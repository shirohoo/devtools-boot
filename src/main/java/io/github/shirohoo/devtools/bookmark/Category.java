package io.github.shirohoo.devtools.bookmark;

import io.github.shirohoo.devtools.config.BaseEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Category extends BaseEntity {
    private String category;

    private Category(Long id, String category) {
        super.id = id;
        this.category = category;
    }

    static Category of(String category) {
        return new Category(null, category);
    }
}
