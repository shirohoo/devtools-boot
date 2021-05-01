package toy.subscribe.domain.board;

import lombok.*;
import toy.subscribe.common.BaseTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedBoard extends BaseTime implements Serializable {
    
    @Column(name = "feed_board_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String company;
    
    private String imgPath;
    
    private String title;
    
    private String guid;
    
}
