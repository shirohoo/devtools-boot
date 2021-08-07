package toy.subscribe.feedboard.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.configs.model.BaseEntity;
import toy.subscribe.feedboard.dto.FeedBoardResponseDto;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_board_id")
    private Long id;
    private String company;
    private String imgPath;
    private String title;
    private String guid;

    @Builder(access = AccessLevel.PUBLIC)
    public FeedBoard(final Long id, final String company, final String imgPath, final String title, final String guid) {
        this.id = id;
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.guid = guid;
    }

    public FeedBoardResponseDto convert() {
        return FeedBoardResponseDto.builder()
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
