package toy.subscribe.configs.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseWrapper<T> {
    private Page<T> pages;
    private Long visitorsOfReduce;
    private Long visitorsOfDay;
}
