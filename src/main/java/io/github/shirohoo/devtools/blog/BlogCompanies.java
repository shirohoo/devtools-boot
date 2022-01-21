package io.github.shirohoo.devtools.blog;

import io.github.shirohoo.devtools.config.BaseEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class BlogCompanies extends BaseEntity {
    private String category;

    private BlogCompanies(Long id, String category) {
        super.id = id;
        this.category = category;
    }

    static BlogCompanies of(String category) {
        return new BlogCompanies(null, category);
    }
}
