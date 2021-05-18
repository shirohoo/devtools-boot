package toy.subscribe.domain.dictionary.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DictionaryResponseWrapper<T> {
    
    private Page<DictionaryResponse> pages;
    private Long visitorsOfReduce;
    private Long visitorsOfDay;
    
}