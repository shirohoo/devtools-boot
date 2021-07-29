package toy.subscribe.configs.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
