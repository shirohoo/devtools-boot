package toy.subscribe.domain.board.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RSSFeedMessage {
    private String title;
    private String company;
    private String imgPath;
    private String link;
    private String author;
    private String guid;
    private String pubDate;
}


