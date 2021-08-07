package toy.subscribe.feedboard.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.configs.model.BaseEntity;
import toy.subscribe.feedboard.dto.FeedBoardResponseDto;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoard extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_board_id")
    private Long id;
    private String company;
    private String imgPath;
    private String title;
    private String guid;

    @Builder(access = AccessLevel.PUBLIC)
    private FeedBoard(String company, String imgPath, String title, String guid) {
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
}
