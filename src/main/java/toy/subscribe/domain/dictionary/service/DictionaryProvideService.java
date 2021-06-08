package toy.subscribe.domain.dictionary.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.dictionary.dto.DictionaryResponseWrapper;

public interface DictionaryProvideService {
    DictionaryResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord);
}
