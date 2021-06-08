package toy.subscribe.domain.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.domain.dictionary.dto.DictionaryResponse;

public interface DictionaryCustomRepository {
    Page<DictionaryResponse> getPageByDictionaries(Pageable pageable, String en, String kr);
}
