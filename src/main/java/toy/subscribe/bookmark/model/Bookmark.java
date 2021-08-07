package toy.subscribe.bookmark.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.bookmark.dto.BookmarkDto;
import toy.subscribe.configs.model.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;
    private String category;
    private String title;
    private String link;

    @Builder(access = AccessLevel.PUBLIC)
    private Bookmark(final String category, final String title, final String link) {
        this.category = category;
        this.title = title;
        this.link = link;
    }

    public BookmarkDto convert() {
        return BookmarkDto.builder()
                          .id(this.id)
                          .category(this.category)
                          .title(this.title)
                          .link(this.link)
                          .build();
    }
}
