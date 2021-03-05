package toy.feed.domain;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class FeedBoard {
    
    @Id @GeneratedValue
    @Column (name = "feed_board_id")
    private Long id;
    
    private String company;
    
    private String title;
    
    private String guid;
    
}
