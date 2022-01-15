package toy.subscribe.config.http.wrapper;

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
    private Long visitorsOfReduce;

    @Builder(access = AccessLevel.PUBLIC)
    private HttpResponseWrapper(final Page<T> pages, final Long visitorsOfDay, final Long visitorsOfReduce) {
        this.pages = pages;
        this.visitorsOfDay = visitorsOfDay;
        this.visitorsOfReduce = visitorsOfReduce;
    }
}
