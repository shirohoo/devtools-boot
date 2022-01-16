package io.github.shirohoo.devtools.dictionary;

import lombok.Builder;
import lombok.Value;

@Value
class DictionaryDto {
    Long id;
    String enWord;
    String krWord;

    @Builder
    private DictionaryDto(Long id, String enWord, String krWord) {
        this.id = id;
        this.enWord = enWord;
        this.krWord = krWord;
    }
}
