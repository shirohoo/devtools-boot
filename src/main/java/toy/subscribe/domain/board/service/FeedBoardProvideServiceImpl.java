package toy.subscribe.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.board.dto.FeedBoardResponseWrapper;
import toy.subscribe.domain.board.repository.FeedBoardRepository;
import toy.subscribe.domain.logging.repository.RequestLogRepository;

@Service
@RequiredArgsConstructor
public class FeedBoardProvideServiceImpl implements FeedBoardProvideService {
    
    private final FeedBoardRepository feedBoardRepository;
    private final RequestLogRepository requestLogRepository;
    
    @Override
    @Transactional(readOnly = true)
    public FeedBoardResponseWrapper provideFeedBoardWrapper(Pageable pageable, String company, String title) {
        return new FeedBoardResponseWrapper(feedBoardRepository.getPageByFeedBoard(pageable, company, title),
                                            requestLogRepository.getDAU(),
                                            requestLogRepository.getCumulativeVisitors());
    }
    
}
