package toy.subscribe.domain.dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.subscribe.domain.dictionary.model.Dictionary;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>,
                                              QuerydslPredicateExecutor<Dictionary>,
                                              DictionaryCustomRepository {
    
}
