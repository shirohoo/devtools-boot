package io.github.shirohoo.devtools.dictionary;

import io.github.shirohoo.devtools.config.model.BaseEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Dictionary extends BaseEntity {
    private String enWord;
    private String krWord;

    private Dictionary(String enWord, String krWord) {
        this.enWord = enWord;
        this.krWord = krWord;
    }

    public static Dictionary of(String enWord, String krWord) {
        return new Dictionary(enWord, krWord);
    }

    DictionaryDto toDto() {
        return DictionaryDto.builder()
            .id(id)
            .enWord(enWord)
            .krWord(krWord)
            .build();
    }
}
