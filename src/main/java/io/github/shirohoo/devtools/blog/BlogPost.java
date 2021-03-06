package io.github.shirohoo.devtools.blog;

import io.github.shirohoo.devtools.config.BaseEntity;
import java.util.Objects;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class BlogPost extends BaseEntity {
    private String company;
    private String imgPath;
    private String title;
    private String guid;

    @Builder
    private BlogPost(String company, String imgPath, String title, String guid) {
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.guid = guid;
    }

    BlogPostDto toDto() {
        return BlogPostDto.builder()
            .id(id)
            .company(company)
            .imgPath(imgPath)
            .title(title)
            .link(guid)
            .regDate(regDate)
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlogPost blogPost = (BlogPost) o;
        return getId().equals(blogPost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
