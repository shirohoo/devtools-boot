package toy.subscribe.common.dtos;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DictionaryResponse {
    private Long id;
    private String enWord;
    private String krWord;
    
    @Builder
    public DictionaryResponse(Long id, String enWord, String krWord) {
        this.id = id;
        this.enWord = enWord;
        this.krWord = krWord;
    }
}
