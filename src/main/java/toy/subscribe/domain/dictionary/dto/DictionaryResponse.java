package toy.subscribe.domain.dictionary.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DictionaryResponse {
    
    private Long id;
    private String enWord;
    private String krWord;
    
}
