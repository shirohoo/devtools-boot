package toy.subscribe.domain.dictionary.model;

import lombok.*;
import toy.subscribe.common.BaseTime;
import toy.subscribe.domain.dictionary.dto.DictionaryResponse;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dictionary extends BaseTime implements Serializable {
    
    @Column(name = "dictionary_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String enWord;
    
    private String krWord;
    
    public DictionaryResponse convertToDictionaryDto() {
        return DictionaryResponse.builder()
                                 .id(this.getId())
                                 .enWord(this.enWord)
                                 .krWord(this.krWord)
                                 .build();
    }
    
}
