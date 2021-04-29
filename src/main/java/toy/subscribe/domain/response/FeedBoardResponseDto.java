package toy.subscribe.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import toy.subscribe.domain.entity.FeedBoard;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoardResponseDto {
    
    private Long id;
    private String company;
    private String imgPath;
    private String title;
    private String link;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    
    public static FeedBoardResponseDto convertToFeedBoardDtoFrom(FeedBoard board) {
        return FeedBoardResponseDto.builder()
                                   .id(board.getId())
                                   .company(board.getCompany())
                                   .imgPath(board.getImgPath())
                                   .title(board.getTitle())
                                   .link(board.getGuid())
                                   .regDate(board.getRegDate())
                                   .build();
    }
    
}
