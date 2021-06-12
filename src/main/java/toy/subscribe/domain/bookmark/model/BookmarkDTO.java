package toy.subscribe.domain.bookmark.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDTO {
    private Long id;
    private String category;
    private String title;
    private String link;
    
    public Bookmark toEntity() {
        return Bookmark.builder()
                       .id(this.id)
                       .category(this.category)
                       .title(this.title)
                       .link(this.link)
                       .build();
    }
    
}
