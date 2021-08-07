package toy.subscribe.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.dictionary.model.Dictionary;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, DictionaryQueryRepository {
    boolean existsByEnWord(final String enWord);
}
