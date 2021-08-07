package toy.subscribe.dictionary.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.dictionary.dto.DictionaryDto;
import toy.subscribe.dictionary.model.Dictionary;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static toy.subscribe.dictionary.model.QDictionary.dictionary;

@RequiredArgsConstructor
public class DictionaryQueryRepositoryImpl implements DictionaryQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<DictionaryDto> findPage(final Pageable pageable, final String enWord, final String krWord) {
        return PageableExecutionUtils.getPage(convert(queryFactory
                                                              .select(dictionary)
                                                              .from(dictionary)
                                                              .where(allContains(enWord, krWord))
                                                              .offset(pageable.getOffset())
                                                              .limit(pageable.getPageSize())
                                                              .orderBy(dictionary.id.desc())
                                                              .fetch()),
                                              pageable,
                                              getCountQuery(enWord, krWord)::fetchCount);
    }

    private List<DictionaryDto> convert(final List<Dictionary> feedBoards) {
        return feedBoards.stream().map(Dictionary::convert).collect(toList());
    }

    private BooleanExpression allContains(final String enWord, final String krWord) {
        return enWordContains(enWord).and(krWordContains(krWord));
    }

    private BooleanExpression enWordContains(final String enWord) {
        return Objects.nonNull(enWord) ? dictionary.enWord.contains(enWord) : null;
    }

    private BooleanExpression krWordContains(final String krWord) {
        return Objects.nonNull(krWord) ? dictionary.krWord.contains(krWord) : null;
    }

    private JPAQuery<Dictionary> getCountQuery(final String enWord, final String krWord) {
        return queryFactory.selectFrom(dictionary).where(allContains(enWord, krWord));
    }
}
