package toy.subscribe.bookmark.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.bookmark.dto.BookmarkDto;
import toy.subscribe.bookmark.model.Bookmark;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static toy.subscribe.bookmark.model.QBookmark.bookmark;

@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<BookmarkDto> findPage(Pageable pageable, String category, String title) {
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

    private List<BookmarkDto> convert(List<Bookmark> feedBoards) {
        return feedBoards.stream()
                         .map(Bookmark::convert)
                         .collect(toList());
    }

    private BooleanExpression allContains(String category, String title) {
        return categoryContains(category).and(titleContains(title));
    }

    private BooleanExpression categoryContains(String category) {
        return Objects.nonNull(category) ? bookmark.category.contains(category) : null;
    }

    private BooleanExpression titleContains(String title) {
        return Objects.nonNull(title) ? bookmark.title.contains(title) : null;
    }

    private JPAQuery<Bookmark> getCountQuery(String category, String title) {
        return queryFactory
                .selectFrom(bookmark)
                .where(allContains(category, title));
    }
}