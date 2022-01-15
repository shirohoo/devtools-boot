package io.github.shirohoo.devtools.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.github.shirohoo.devtools.dictionary.dto.DictionaryDto;

public interface DictionaryQueryRepository {
    Page<DictionaryDto> findPage(Pageable pageable, String en, String kr);
}
