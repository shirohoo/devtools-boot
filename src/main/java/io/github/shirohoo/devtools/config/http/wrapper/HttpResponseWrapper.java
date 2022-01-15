package io.github.shirohoo.devtools.config.http.wrapper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseWrapper<T> {
    private Page<T> pages;
    private Long visitorsOfDay;
    private Long visitorsOfTotal;

    @Builder(access = AccessLevel.PUBLIC)
    private HttpResponseWrapper(final Page<T> pages, final Long visitorsOfDay, final Long visitorsOfTotal) {
        this.pages = pages;
        this.visitorsOfDay = visitorsOfDay;
        this.visitorsOfTotal = visitorsOfTotal;
    }
}
