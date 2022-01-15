package io.github.shirohoo.devtools.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DevtoolsResponse<T> {
    private Page<T> pages;
    private Long visitorsOfDay;
    private Long visitorsOfTotal;

    @Builder
    private DevtoolsResponse(Page<T> pages, Long visitorsOfDay, Long visitorsOfTotal) {
        this.pages = pages;
        this.visitorsOfDay = visitorsOfDay;
        this.visitorsOfTotal = visitorsOfTotal;
    }
}
