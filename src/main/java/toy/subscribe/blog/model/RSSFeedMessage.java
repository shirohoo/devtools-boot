package toy.subscribe.blog.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RSSFeedMessage {
    private String title;
    private String company;
    private String imgPath;
    private String link;
    private String author;
    private String guid;
    private String pubDate;

    @Builder(access = AccessLevel.PUBLIC)
    private RSSFeedMessage(final String title, final String company, final String imgPath, final String link, final String author, final String guid, final String pubDate) {
        this.title = title;
        this.company = company;
        this.imgPath = imgPath;
        this.link = link;
        this.author = author;
        this.guid = guid;
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }
        final RSSFeedMessage that = (RSSFeedMessage) o;
        return Objects.equals(getGuid(), that.getGuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGuid());
    }
}


