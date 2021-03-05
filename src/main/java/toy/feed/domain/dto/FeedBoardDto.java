package toy.feed.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class FeedBoardDto {
    
    private Long id;
    
    private String company;
    
    private String imgPath;
    
    private String title;
    
    private String link;
    
    @JsonFormat (shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDateTime regDate;
    
}
