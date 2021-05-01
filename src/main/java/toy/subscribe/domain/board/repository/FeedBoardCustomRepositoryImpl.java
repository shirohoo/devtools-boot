package toy.subscribe.domain.board.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.board.FeedBoard;
import toy.subscribe.domain.board.dto.FeedBoardResponseDto;

import java.util.List;
import java.util.stream.Collectors;

import static toy.subscribe.domain.board.QFeedBoard.feedBoard;

public class FeedBoardCustomRepositoryImpl extends QuerydslRepositorySupport implements FeedBoardCustomRepository {
    
    public FeedBoardCustomRepositoryImpl () {
        super(FeedBoard.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<FeedBoardResponseDto> findPageByFeedBoard(Pageable pageable, String company, String title) {
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QueryResults<FeedBoard> results = queryFactory
                .select(feedBoard)
                .from(feedBoard)
                .where(feedBoard.company.contains(company))
                .where(feedBoard.title.contains(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(feedBoard.id.desc())
                .fetchResults();
        
        List<FeedBoardResponseDto> content = results.getResults()
                                                    .stream()
                                                    .map(FeedBoardResponseDto::convertToFeedBoardDtoFrom)
                                                    .collect(Collectors.toList());
        
        return new PageImpl<>(content, pageable, results.getTotal());
    }
    
}
