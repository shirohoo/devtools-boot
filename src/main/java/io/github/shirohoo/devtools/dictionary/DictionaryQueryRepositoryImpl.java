package io.github.shirohoo.devtools.dictionary;

import static io.github.shirohoo.devtools.dictionary.model.QDictionary.dictionary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class DictionaryQueryRepositoryImpl implements DictionaryQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<Dictionary> findPages(Pageable pageable, String enWord, String krWord) {
        return PageableExecutionUtils.getPage(
            queryFactory
                .select(dictionary)
                .from(dictionary)
                .where(allContains(enWord, krWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(dictionary.id.desc())
                .fetch(),
            pageable,
            getCountQuery(enWord, krWord)::fetchCount);
    }

    private BooleanExpression allContains(String enWord, String krWord) {
        return enWordContains(enWord)
            .and(krWordContains(krWord));
    }

    private BooleanExpression enWordContains(String enWord) {
        return enWord != null ? dictionary.enWord.contains(enWord) : null;
    }

    private BooleanExpression krWordContains(String krWord) {
        return krWord != null ? dictionary.krWord.contains(krWord) : null;
    }

    private JPAQuery<Dictionary> getCountQuery(String enWord, String krWord) {
        return queryFactory.selectFrom(dictionary)
            .where(allContains(enWord, krWord));
    }
}
