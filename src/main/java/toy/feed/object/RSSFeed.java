package toy.feed.object;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RSSFeed {
    
    private final String title;
    private final String link;
    private final String language;
    private final String copyright;
    private final String pubDate;
    private final List<RSSFeedMessage> messages = new ArrayList<>();
    
    public RSSFeed (String title, String link, String language, String copyright, String pubDate) {
        this.title = title;
        this.link = link;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
    }
    
}

