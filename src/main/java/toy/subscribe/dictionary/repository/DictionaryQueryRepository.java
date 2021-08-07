package toy.subscribe.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.dictionary.dto.DictionaryDto;

public interface DictionaryQueryRepository {
    Page<DictionaryDto> findPage(Pageable pageable, String en, String kr);
}
