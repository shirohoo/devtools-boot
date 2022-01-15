package io.github.shirohoo.devtools.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class BlogPostDto {
    private Long id;

    private String company;

    private String imgPath;

    private String title;

    private String link;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime regDate;

    @Builder
    private BlogPostDto(Long id, String company, String imgPath, String title, String link, LocalDateTime regDate) {
        this.id = id;
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.link = link;
        this.regDate = regDate;
    }
}
