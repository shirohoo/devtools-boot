package io.github.shirohoo.devtools.blog;

import org.springframework.data.jpa.repository.JpaRepository;

interface BlogPostRepository extends JpaRepository<BlogPost, Long>, BlogPostQueryRepository {
    boolean existsByGuid(String guid);
}
