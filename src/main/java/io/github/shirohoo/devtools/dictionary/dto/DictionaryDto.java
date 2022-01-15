package io.github.shirohoo.devtools.dictionary.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DictionaryDto {
    private Long id;
    private String enWord;
    private String krWord;

    @Builder(access = AccessLevel.PUBLIC)
    private DictionaryDto(Long id, String enWord, String krWord) {
        this.id = id;
        this.enWord = enWord;
        this.krWord = krWord;
    }
}
