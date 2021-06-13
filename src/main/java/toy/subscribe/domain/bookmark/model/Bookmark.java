package toy.subscribe.domain.bookmark.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark implements Serializable {
    private static final long serialVersionUID = 2674924202908029629L;
    
    @Column(name = "bookmark_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String title;
    private String link;
    
    public BookmarkDto toResponseDto() {
        return BookmarkDto.builder()
                          .id(this.id)
                          .category(this.category)
                          .title(this.title)
                          .link(this.link)
                          .build();
    }
}
