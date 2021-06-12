package toy.subscribe.domain.bookmark.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    @Column(name = "bookmark_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String title;
    private String link;
    
    public BookmarkDTO toResponseDto() {
        return BookmarkDTO.builder()
                          .id(this.id)
                          .category(this.category)
                          .title(this.title)
                          .link(this.link)
                          .build();
    }
}
