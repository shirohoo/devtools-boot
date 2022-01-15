package toy.subscribe.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.config.http.log.repository.HttpLogRepository;
import toy.subscribe.config.http.wrapper.HttpResponseWrapper;
import toy.subscribe.blog.dto.FeedBoardDto;
import toy.subscribe.blog.repository.FeedBoardRepository;

@Service
@RequiredArgsConstructor
public class FeedBoardProvideService {
    private final FeedBoardRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    public HttpResponseWrapper<FeedBoardDto> provideFeedBoardWrapper(Pageable pageable, String company, String title) {
        return HttpResponseWrapper.<FeedBoardDto>builder()
                                  .pages(feedBoardRepository.findPage(pageable, company, title))
                                  .visitorsOfDay(requestLogRepository.findDau())
                                  .visitorsOfReduce(requestLogRepository.findCumulativeVisitors())
                                  .build();
    }
}
