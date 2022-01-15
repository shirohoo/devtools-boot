package io.github.shirohoo.devtools.bookmark;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface BookmarkQueryRepository {
    Page<BookmarkDto> findPage(final Pageable pageable, final String category, final String title);
}
