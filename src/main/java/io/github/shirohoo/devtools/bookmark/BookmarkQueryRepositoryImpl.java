package io.github.shirohoo.devtools.bookmark;

import static io.github.shirohoo.devtools.bookmark.QBookmark.bookmark;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<Bookmark> findPages(Pageable pageable, String category, String title) {
        return PageableExecutionUtils.getPage(
            queryFactory
                .selectFrom(bookmark)
                .where(allContains(category, title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookmark.id.desc())
                .fetch(),
            pageable,
            getCountQuery(category, title)::fetchCount);
    }

    private BooleanExpression allContains(String category, String title) {
        return categoryContains(category)
            .and(titleContains(title));
    }

    private BooleanExpression categoryContains(String category) {
        return category != null ? bookmark.category.contains(category) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? bookmark.title.contains(title) : null;
    }

    private JPAQuery<Bookmark> getCountQuery(String category, String title) {
        return queryFactory.selectFrom(bookmark)
            .where(allContains(category, title));
    }
}
