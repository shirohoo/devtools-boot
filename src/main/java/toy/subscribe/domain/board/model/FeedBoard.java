package toy.subscribe.domain.board.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.common.BaseTime;
import toy.subscribe.common.dtos.FeedBoardResponse;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoard extends BaseTime implements Serializable {
    private static final long serialVersionUID = -6683539796802328761L;
    
    @Column(name = "feed_board_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String imgPath;
    private String title;
    private String guid;
    
    @Builder
    public FeedBoard(Long id, String company, String imgPath, String title, String guid) {
        this.id = id;
        this.company = company;
        this.imgPath = imgPath;
        this.title = title;
        this.guid = guid;
    }
    
    public FeedBoardResponse toFeedBoardResponse() {
        return FeedBoardResponse.builder()
                                .id(this.getId())
                                .company(this.getCompany())
                                .imgPath(this.getImgPath())
                                .title(this.getTitle())
                                .link(this.getGuid())
                                .regDate(this.getRegDate())
                                .build();
    }
}
