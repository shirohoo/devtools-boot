package toy.subscribe.dictionary.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.dtos.ResponseWrapper;

public interface DictionaryProvideService {
    ResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord);
}
