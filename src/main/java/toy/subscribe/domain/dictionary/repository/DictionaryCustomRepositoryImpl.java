package toy.subscribe.domain.dictionary.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.common.dtos.DictionaryResponse;
import toy.subscribe.domain.dictionary.model.Dictionary;

import java.util.List;
import java.util.stream.Collectors;

import static toy.subscribe.domain.dictionary.model.QDictionary.dictionary;

public class DictionaryCustomRepositoryImpl extends QuerydslRepositorySupport implements DictionaryCustomRepository {
    public DictionaryCustomRepositoryImpl() {
        super(Dictionary.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<DictionaryResponse> getPageFromDictionaries(Pageable pageable, String enWord, String krWord) {
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QueryResults<Dictionary> results = queryFactory
                .select(dictionary)
                .from(dictionary)
                .where(dictionary.enWord.contains(enWord)
                                        .and(dictionary.krWord.contains(krWord)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(dictionary.id.desc())
                .fetchResults();
        
        List<DictionaryResponse> content = results.getResults()
                                                  .stream()
                                                  .map(Dictionary::convertToDictionaryDto)
                                                  .collect(Collectors.toList());
        
        return new PageImpl<>(content, pageable, results.getTotal());
    }
}
