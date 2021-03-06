package toy.feed.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class FeedBoard extends BaseTime{
    
    @Id @GeneratedValue
    @Column (name = "feed_board_id")
    private Long id;
    
    private String company;
    
    private String imgPath;
    
    private String title;
    
    private String guid;
    
}
