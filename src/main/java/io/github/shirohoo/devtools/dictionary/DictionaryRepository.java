package io.github.shirohoo.devtools.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;

interface DictionaryRepository extends JpaRepository<Dictionary, Long>, DictionaryQueryRepository {
    boolean existsByEnWord(final String enWord);
}
