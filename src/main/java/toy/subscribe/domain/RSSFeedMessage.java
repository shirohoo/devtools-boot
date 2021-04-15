package toy.subscribe.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RSSFeedMessage {
    
    private String title;
    private String company;
    private String imgPath;
    private String link;
    private String author;
    private String guid;
    private String pubdate;
    
    @Builder
    public RSSFeedMessage(String title, String company, String imgPath, String link, String author, String guid, String pubdate) {
        this.title = title;
        this.company = company;
        this.imgPath = imgPath;
        this.link = link;
        this.author = author;
        this.guid = guid;
        this.pubdate = pubdate;
    }
    
}


