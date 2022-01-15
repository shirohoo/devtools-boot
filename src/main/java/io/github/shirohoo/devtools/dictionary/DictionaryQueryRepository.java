package io.github.shirohoo.devtools.dictionary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface DictionaryQueryRepository {
    Page<Dictionary> findPages(Pageable pageable, String enWord, String krWord);
}
