package toy.subscribe.feedboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoardDto {
    private Long id;
    private String company;
    private String imgPath;
    private String title;
    private String link;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    private FeedBoardDto(final Long id, final String company, final String imgPath, final String title, final String link, final LocalDateTime regDate) {
        this.id = id;
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.link = link;
        this.regDate = regDate;
    }

    @Builder
    public static FeedBoardDto of(final Long id, final String company, final String imgPath, final String title, final String link, final LocalDateTime regDate) {
        return new FeedBoardDto(id, company, imgPath, title, link, regDate);
    }
}
