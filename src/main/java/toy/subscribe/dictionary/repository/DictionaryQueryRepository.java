package toy.subscribe.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.subscribe.dictionary.dto.DictionaryResponseDto;

public interface DictionaryQueryRepository {
    Page<DictionaryResponseDto> findPage(Pageable pageable, String en, String kr);
}
