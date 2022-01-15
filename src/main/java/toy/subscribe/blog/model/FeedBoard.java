package toy.subscribe.blog.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.config.model.BaseEntity;
import toy.subscribe.blog.dto.FeedBoardDto;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoard extends BaseEntity {
    private String company;
    private String imgPath;
    private String title;
    private String guid;

    @Builder(access = AccessLevel.PUBLIC)
    private FeedBoard(final String company, final String imgPath, final String title, final String guid) {
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.guid = guid;
    }

    public FeedBoardDto convert() {
        return FeedBoardDto.builder()
                           .company(this.getCompany())
                           .imgPath(this.getImgPath())
                           .title(this.getTitle())
                           .link(this.getGuid())
                           .regDate(this.getRegDate())
                           .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }
        final FeedBoard feedBoard = (FeedBoard) o;
        return getId().equals(feedBoard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
