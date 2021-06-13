package toy.subscribe.domain.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.common.dtos.DictionaryResponse;

public interface DictionaryCustomRepository {
    Page<DictionaryResponse> getPageFromDictionaries(Pageable pageable, String en, String kr);
}
