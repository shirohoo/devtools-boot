package toy.subscribe.configs.http.wrapper;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseWrapper<T> {
    private Page<T> pages;
    private Long visitorsOfReduce;
    private Long visitorsOfDay;

    @Builder(access = AccessLevel.PUBLIC)
    private HttpResponseWrapper(final Page<T> pages, final Long visitorsOfReduce, final Long visitorsOfDay) {
        this.pages = pages;
        this.visitorsOfReduce = visitorsOfReduce;
        this.visitorsOfDay = visitorsOfDay;
    }
}
