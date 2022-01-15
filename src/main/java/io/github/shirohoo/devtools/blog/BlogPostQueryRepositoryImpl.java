package io.github.shirohoo.devtools.blog;

import static io.github.shirohoo.devtools.blog.QBlogPost.blogPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class BlogPostQueryRepositoryImpl implements BlogPostQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<BlogPost> providePages(Pageable pageable, String company, String title) {
        return PageableExecutionUtils.getPage(
            queryFactory
                .selectFrom(blogPost)
                .where(allContains(company, title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(blogPost.id.desc())
                .fetch(),
            pageable,
            getCountQuery(company, title)::fetchCount);
    }

    private BooleanExpression allContains(String company, String title) {
        return companyContains(company).and(titleContains(title));
    }

    private BooleanExpression companyContains(String company) {
        return Objects.nonNull(company) ? blogPost.company.contains(company) : null;
    }

    private BooleanExpression titleContains(String title) {
        return Objects.nonNull(title) ? blogPost.title.contains(title) : null;
    }

    private JPAQuery<BlogPost> getCountQuery(String company, String title) {
        return queryFactory.selectFrom(blogPost).where(allContains(company, title));
    }
}
