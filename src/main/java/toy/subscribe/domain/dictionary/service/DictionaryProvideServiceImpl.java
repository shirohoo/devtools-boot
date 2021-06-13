package toy.subscribe.domain.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.common.dtos.ResponseWrapper;
import toy.subscribe.common.logging.repository.RequestLogRepository;
import toy.subscribe.domain.dictionary.repository.DictionaryRepository;

@Service
@RequiredArgsConstructor
public class DictionaryProvideServiceImpl implements DictionaryProvideService {
    private final DictionaryRepository feedBoardRepository;
    private final RequestLogRepository requestLogRepository;
    
    @Override
    @Transactional(readOnly = true)
    public ResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord) {
        return new ResponseWrapper(feedBoardRepository.getPageFromDictionaries(pageable, enWord, krWord),
                                   requestLogRepository.getCumulativeVisitors(),
                                   requestLogRepository.getDAU());
    }
}
