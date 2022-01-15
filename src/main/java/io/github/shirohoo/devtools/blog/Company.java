package io.github.shirohoo.devtools.blog;

import io.github.shirohoo.devtools.config.model.BaseEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Company extends BaseEntity {
    private String url;
    private String key;
    private String name;
    private String imgPath;
}
