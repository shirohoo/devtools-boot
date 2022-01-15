package io.github.shirohoo.devtools.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentProvider<T> {
    Page<T> provide(Pageable pageable, String company, String title);

    Long visitorsOfDay();

    Long visitorsOfTotal();
}
