package io.github.shirohoo.devtools;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentProvider<T> {
    Page<T> provide(Pageable pageable, String c1, String c2);

    Long visitorsOfDay();

    Long visitorsOfTotal();
}
