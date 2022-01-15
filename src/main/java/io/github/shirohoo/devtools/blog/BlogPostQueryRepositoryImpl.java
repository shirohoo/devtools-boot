package io.github.shirohoo.devtools.blog;

import static java.util.stream.Collectors.toList;
import static io.github.shirohoo.devtools.blog.QBlogPost.*;
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
class BlogPostQueryRepositoryImpl implements BlogPostQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<BlogPostDto> findPage(Pageable pageable, String company, String title) {
        return PageableExecutionUtils.getPage(
            convert(queryFactory
                .selectFrom(blogPost)
                .where(allContains(company, title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(blogPost.id.desc())
                .fetch()),
            pageable,
            getCountQuery(company, title)::fetchCount);
    }

    private List<BlogPostDto> convert(List<BlogPost> blogPosts) {
        return blogPosts.stream().map(BlogPost::toDto).collect(toList());
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
