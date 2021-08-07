package toy.subscribe.dictionary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.configs.model.BaseEntity;
import toy.subscribe.dictionary.dto.DictionaryDto;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dictionary extends BaseEntity {
    private String enWord;
    private String krWord;

    private Dictionary(String enWord, String krWord) {
        this.enWord = enWord;
        this.krWord = krWord;
    }

    public static Dictionary of(String enWord, String krWord) {
        return new Dictionary(enWord, krWord);
    }

    public DictionaryDto convert() {
        return DictionaryDto.builder()
                            .id(super.getId())
                            .enWord(this.enWord)
                            .krWord(this.krWord)
                            .build();
    }
}
