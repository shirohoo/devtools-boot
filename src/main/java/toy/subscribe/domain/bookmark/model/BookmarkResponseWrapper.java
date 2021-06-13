package toy.subscribe.domain.bookmark.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkResponseWrapper<T> {
    private Page<BookmarkDto> pages;
    private Long visitorsOfReduce;
    private Long visitorsOfDay;
}
