package toy.subscribe.bookmark;

import static java.util.stream.Collectors.toList;
import static toy.subscribe.bookmark.QBookmark.bookmark;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
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
    public Page<BookmarkDto> findPage(final Pageable pageable, final String category, final String title) {
        return PageableExecutionUtils.getPage(convert(queryFactory
                .selectFrom(bookmark)
                .where(allContains(category, title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookmark.id.desc())
                .fetch()),
            pageable,
            getCountQuery(category, title)::fetchCount);
    }

    private List<BookmarkDto> convert(final List<Bookmark> feedBoards) {
        return feedBoards.stream().map(Bookmark::toDto).collect(toList());
    }

    private BooleanExpression allContains(final String category, final String title) {
        return categoryContains(category).and(titleContains(title));
    }

    private BooleanExpression categoryContains(final String category) {
        return Objects.nonNull(category) ? bookmark.category.contains(category) : null;
    }

    private BooleanExpression titleContains(final String title) {
        return Objects.nonNull(title) ? bookmark.title.contains(title) : null;
    }

    private JPAQuery<Bookmark> getCountQuery(final String category, final String title) {
        return queryFactory.selectFrom(bookmark).where(allContains(category, title));
    }
}
