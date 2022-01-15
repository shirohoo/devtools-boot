package io.github.shirohoo.devtools.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.shirohoo.devtools.dictionary.model.Dictionary;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, DictionaryQueryRepository {
    boolean existsByEnWord(final String enWord);
}
