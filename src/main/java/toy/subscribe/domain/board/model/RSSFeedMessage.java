package toy.subscribe.domain.board.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RSSFeedMessage {
    private String title;
    private String company;
    private String imgPath;
    private String link;
    private String author;
    private String guid;
    private String pubDate;
    
    @Builder
    public RSSFeedMessage(String title, String company, String imgPath, String link, String author, String guid, String pubDate) {
        this.title = title;
        this.company = company;
        this.imgPath = imgPath;
        this.link = link;
        this.author = author;
        this.guid = guid;
        this.pubDate = pubDate;
    }
}


