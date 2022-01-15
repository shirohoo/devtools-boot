package io.github.shirohoo.devtools.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface BlogPostQueryRepository {
    Page<BlogPost> providePages(Pageable pageable, String company, String title);
}
