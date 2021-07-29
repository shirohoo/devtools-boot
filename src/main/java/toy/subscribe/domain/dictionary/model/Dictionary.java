package toy.subscribe.domain.dictionary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.configs.dtos.DictionaryResponse;
import toy.subscribe.configs.model.BaseEntity;

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

    public DictionaryResponse convert() {
        return DictionaryResponse.builder()
                                 .id(this.getId())
                                 .enWord(this.enWord)
                                 .krWord(this.krWord)
                                 .build();
    }
}
