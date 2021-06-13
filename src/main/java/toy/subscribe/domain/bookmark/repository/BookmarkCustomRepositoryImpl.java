package toy.subscribe.domain.bookmark.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.bookmark.model.Bookmark;
import toy.subscribe.domain.bookmark.model.BookmarkDto;

import java.util.List;
import java.util.stream.Collectors;

import static toy.subscribe.domain.bookmark.model.QBookmark.bookmark;

public class BookmarkCustomRepositoryImpl extends QuerydslRepositorySupport implements BookmarkCustomRepository {
    public BookmarkCustomRepositoryImpl() {
        super(Bookmark.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<BookmarkDto> getPageFromBookmark(Pageable pageable, String category, String title) {
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QueryResults<Bookmark> results = queryFactory
                .select(bookmark)
                .from(bookmark)
                .where(bookmark.category.contains(category)
                                        .and(bookmark.title.contains(title)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookmark.id.desc())
                .fetchResults();
        
        List<BookmarkDto> content = results.getResults()
                                           .stream()
                                           .map(Bookmark::toResponseDto)
                                           .collect(Collectors.toList());
        
        return new PageImpl<>(content, pageable, results.getTotal());
    }
}
