package toy.subscribe.domain.dictionary.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.common.dtos.ResponseWrapper;

public interface DictionaryProvideService {
    ResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord);
}
