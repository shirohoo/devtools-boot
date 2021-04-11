package toy.feed.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RSSFeedMessage {
    
    private String title;
    private String company;
    private String imgPath;
    private String link;
    private String author;
    private String guid;
    private String pubdate;
    
}


