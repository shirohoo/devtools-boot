package toy.subscribe.feedboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;
import toy.subscribe.configs.http.wrapper.HttpResponseWrapper;
import toy.subscribe.feedboard.dto.FeedBoardDto;
import toy.subscribe.feedboard.repository.FeedBoardRepository;

@Service
@RequiredArgsConstructor
public class FeedBoardProvideService {
    private final FeedBoardRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    public HttpResponseWrapper<FeedBoardDto> provideFeedBoardWrapper(Pageable pageable, String company, String title) {
        return HttpResponseWrapper.<FeedBoardDto>builder()
                                  .pages(feedBoardRepository.findPage(pageable, company, title))
                                  .visitorsOfDay(requestLogRepository.findCumulativeVisitors())
                                  .visitorsOfReduce(requestLogRepository.findDau())
                                  .build();
    }
}
