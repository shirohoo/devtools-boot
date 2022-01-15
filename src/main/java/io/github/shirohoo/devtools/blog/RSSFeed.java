package io.github.shirohoo.devtools.blog;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class RSSFeed {
    private String title;
    private String link;
    private String language;
    private String copyright;
    private String pubDate;
    private List<RSSFeedMessage> messages = new ArrayList<>();

    private RSSFeed(String title, String link, String language, String copyright, String pubDate) {
        this(title, link, language, copyright, pubDate, new ArrayList<>());
    }

    private RSSFeed(String title, String link, String language, String copyright, String pubDate, List<RSSFeedMessage> messages) {
        this.title = title;
        this.link = link;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
        this.messages = messages;
    }

    static RSSFeed of(String title, String link, String language, String copyright, String pubDate) {
        return new RSSFeed(title, link, language, copyright, pubDate);
    }

    static RSSFeed of(String title, String link, String language, String copyright, String pubDate, List<RSSFeedMessage> messages) {
        return new RSSFeed(title, link, language, copyright, pubDate, messages);
    }
}

