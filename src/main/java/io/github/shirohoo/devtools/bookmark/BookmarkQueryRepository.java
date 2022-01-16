package io.github.shirohoo.devtools.bookmark;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface BookmarkQueryRepository {
    Page<Bookmark> findPages(Pageable pageable, String category, String title);
}
