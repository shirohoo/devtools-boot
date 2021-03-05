package toy.feed.object;

import lombok.*;

@ToString
@Getter @Setter
public class RSSFeedMessage {
    
    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;
    private String pubdate;
    
}


