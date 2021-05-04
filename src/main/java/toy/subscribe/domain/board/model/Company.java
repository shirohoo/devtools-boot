package toy.subscribe.domain.board.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    
    private String key;
    private String name;
    private String imgPath;
    
}
