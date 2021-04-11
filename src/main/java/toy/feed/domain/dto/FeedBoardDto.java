package toy.feed.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import toy.feed.domain.FeedBoard;

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
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    
    public static FeedBoardDto ConvertToDtoFrom (FeedBoard board) {
        return FeedBoardDto.builder()
                           .id(board.getId())
                           .company(board.getCompany())
                           .imgPath(board.getImgPath())
                           .title(board.getTitle())
                           .link(board.getGuid())
                           .regDate(board.getRegDate())
                           .build();
    }
    
}
