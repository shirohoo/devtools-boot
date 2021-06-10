package toy.subscribe.domain.board.model;

import lombok.*;
import toy.subscribe.common.BaseTime;
import toy.subscribe.domain.board.dto.FeedBoardResponse;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
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
    
    public FeedBoardResponse convertToFeedBoardDto() {
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
