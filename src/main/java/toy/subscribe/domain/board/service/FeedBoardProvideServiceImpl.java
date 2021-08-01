package toy.subscribe.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.configs.dtos.ResponseWrapper;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;
import toy.subscribe.domain.board.repository.FeedBoardRepository;

@Service
@RequiredArgsConstructor
public class FeedBoardProvideServiceImpl implements FeedBoardProvideService {
    private final FeedBoardRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseWrapper provideFeedBoardWrapper(Pageable pageable, String company, String title) {
        return new ResponseWrapper(feedBoardRepository.findPage(pageable, company, title),
                                   requestLogRepository.findCumulativeVisitors(),
                                   requestLogRepository.findDau());
    }
}
