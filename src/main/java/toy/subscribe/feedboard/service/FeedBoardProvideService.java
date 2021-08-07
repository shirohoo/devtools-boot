package toy.subscribe.feedboard.service;

import org.springframework.data.domain.Pageable;
import toy.subscribe.configs.http.wrapper.HttpResponseWrapper;

public interface FeedBoardProvideService {
    HttpResponseWrapper provideFeedBoardWrapper(Pageable pageable, String company, String title);
}
