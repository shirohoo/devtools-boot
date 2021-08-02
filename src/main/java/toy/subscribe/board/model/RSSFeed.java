package toy.subscribe.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RSSFeed {
    private String title;
    private String link;
    private String language;
    private String copyright;
    private String pubDate;
    private List<RSSFeedMessage> messages = new ArrayList<>();

    public RSSFeed(String title, String link, String language, String copyright, String pubDate) {
        this(title, link, language, copyright, pubDate, new ArrayList<>());
    }

    public RSSFeed(String title, String link, String language, String copyright, String pubDate, List<RSSFeedMessage> messages) {
        this.title = title;
        this.link = link;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
        this.messages = messages;
    }
}

