package toy.feed.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
