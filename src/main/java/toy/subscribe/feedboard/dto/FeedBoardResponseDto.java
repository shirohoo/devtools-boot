package toy.subscribe.feedboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoardResponseDto {
    private Long id;
    private String company;
    private String imgPath;
    private String title;
    private String link;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private FeedBoardResponseDto(final Long id, final String company, final String imgPath, final String title, final String link, final LocalDateTime regDate) {
        this.id = id;
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.link = link;
        this.regDate = regDate;
    }

    @Builder
    public static FeedBoardResponseDto of(final Long id, final String company, final String imgPath, final String title, final String link, final LocalDateTime regDate) {
        return new FeedBoardResponseDto(id, company, imgPath, title, link, regDate);
    }
}
