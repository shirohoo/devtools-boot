package toy.subscribe.feedboard.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.feedboard.dto.FeedBoardResponseDto;
import toy.subscribe.feedboard.model.FeedBoard;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static toy.subscribe.feedboard.model.QFeedBoard.feedBoard;

@RequiredArgsConstructor
public class FeedBoardQueryRepositoryImpl implements FeedBoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<FeedBoardResponseDto> findPage(Pageable pageable, String company, String title) {
        return PageableExecutionUtils.getPage(convert(queryFactory
                                                              .selectFrom(feedBoard)
                                                              .where(allContains(company, title))
                                                              .offset(pageable.getOffset())
                                                              .limit(pageable.getPageSize())
                                                              .orderBy(feedBoard.id.desc())
                                                              .fetch()),
                                              pageable,
                                              getCountQuery(company, title)::fetchCount);
    }

    private List<FeedBoardResponseDto> convert(List<FeedBoard> feedBoards) {
        return feedBoards.stream()
                         .map(FeedBoard::convert)
                         .collect(toList());
    }

    private BooleanExpression allContains(String company, String title) {
        return companyContains(company).and(titleContains(title));
    }

    private BooleanExpression companyContains(String company) {
        return Objects.nonNull(company) ? feedBoard.company.contains(company) : null;
    }

    private BooleanExpression titleContains(String title) {
        return Objects.nonNull(title) ? feedBoard.title.contains(title) : null;
    }

    private JPAQuery<FeedBoard> getCountQuery(String company, String title) {
        return queryFactory
                .selectFrom(feedBoard)
                .where(allContains(company, title));
    }
}
