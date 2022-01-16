package io.github.shirohoo.devtools.blog;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class RSSFeedMessage {
    private String title;
    private String company;
    private String imgPath;
    private String link;
    private String author;
    private String guid;
    private String pubDate;

    @Builder(access = AccessLevel.PUBLIC)
    private RSSFeedMessage(String title, String company, String imgPath, String link, String author, String guid, String pubDate) {
        this.title = title;
        this.company = company;
        this.imgPath = imgPath;
        this.link = link;
        this.author = author;
        this.guid = guid;
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RSSFeedMessage that = (RSSFeedMessage) o;
        return Objects.equals(getGuid(), that.getGuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGuid());
    }
}


