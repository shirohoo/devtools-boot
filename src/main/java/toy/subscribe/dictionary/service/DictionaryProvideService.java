package toy.subscribe.dictionary.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.http.wrapper.HttpResponseWrapper;

public interface DictionaryProvideService {
    HttpResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord);
}
