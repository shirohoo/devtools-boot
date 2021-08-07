package toy.subscribe.dictionary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.configs.model.BaseEntity;
import toy.subscribe.dictionary.dto.DictionaryDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dictionary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dictionary_id")
    private Long id;
    private String enWord;
    private String krWord;

    private Dictionary(String enWord, String krWord) {
        this.enWord = enWord;
        this.krWord = krWord;
    }

    private Dictionary(final Long id, final String enWord, final String krWord) {
        this.id = id;
        this.enWord = enWord;
        this.krWord = krWord;
    }

    public static Dictionary of(String enWord, String krWord) {
        return new Dictionary(enWord, krWord);
    }

    public static Dictionary of(final Long id, final String enWord, final String krWord) {
        return new Dictionary(id, enWord, krWord);
    }

    public DictionaryDto convert() {
        return DictionaryDto.builder()
                            .id(this.getId())
                            .enWord(this.enWord)
                            .krWord(this.krWord)
                            .build();
    }
}
