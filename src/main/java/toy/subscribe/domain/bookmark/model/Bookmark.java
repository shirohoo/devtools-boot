package toy.subscribe.domain.bookmark.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.common.dtos.BookmarkDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark implements Serializable {
    private static final long serialVersionUID = 2674924202908029629L;
    
    @Column(name = "bookmark_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String title;
    private String link;
    
    @Builder
    public Bookmark(Long id, String category, String title, String link) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.link = link;
    }
    
    public BookmarkDto toResponseDto() {
        return BookmarkDto.builder()
                          .id(this.id)
                          .category(this.category)
                          .title(this.title)
                          .link(this.link)
                          .build();
    }
}
