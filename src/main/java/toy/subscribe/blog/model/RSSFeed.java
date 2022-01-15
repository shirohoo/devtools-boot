package toy.subscribe.blog.model;

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

    private RSSFeed(final String title, final String link, final String language, final String copyright, final String pubDate) {
        this(title, link, language, copyright, pubDate, new ArrayList<>());
    }

    private RSSFeed(final String title, final String link, final String language, final String copyright, final String pubDate, final List<RSSFeedMessage> messages) {
        this.title = title;
        this.link = link;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
        this.messages = messages;
    }

    public static RSSFeed of(final String title, final String link, final String language, final String copyright, final String pubDate) {
        return new RSSFeed(title, link, language, copyright, pubDate);
    }

    public static RSSFeed of(final String title, final String link, final String language, final String copyright, final String pubDate, final List<RSSFeedMessage> messages) {
        return new RSSFeed(title, link, language, copyright, pubDate, messages);
    }
}

