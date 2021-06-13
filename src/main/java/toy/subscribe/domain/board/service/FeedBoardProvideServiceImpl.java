package toy.subscribe.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.common.logging.repository.RequestLogRepository;
import toy.subscribe.domain.board.repository.FeedBoardRepository;
import toy.subscribe.common.dtos.ResponseWrapper;

@Service
@RequiredArgsConstructor
public class FeedBoardProvideServiceImpl implements FeedBoardProvideService {
    private final FeedBoardRepository feedBoardRepository;
    private final RequestLogRepository requestLogRepository;
    
    @Override
    @Transactional(readOnly = true)
    public ResponseWrapper provideFeedBoardWrapper(Pageable pageable, String company, String title) {
        return new ResponseWrapper(feedBoardRepository.getPageFromFeedBoard(pageable, company, title),
                                   requestLogRepository.getCumulativeVisitors(),
                                   requestLogRepository.getDAU());
    }
}
