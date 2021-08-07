package toy.subscribe.dictionary.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DictionaryResponseDto {
    private Long id;
    private String enWord;
    private String krWord;

    @Builder(access = AccessLevel.PUBLIC)
    private DictionaryResponseDto(Long id, String enWord, String krWord) {
        this.id = id;
        this.enWord = enWord;
        this.krWord = krWord;
    }
}
