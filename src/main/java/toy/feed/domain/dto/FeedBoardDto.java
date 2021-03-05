package toy.feed.domain.dto;

import lombok.*;

@Builder
@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class FeedBoardDto {
    
    private Long id;
    
    private String company;
    
    private String title;
    
    private String link;
    
}
