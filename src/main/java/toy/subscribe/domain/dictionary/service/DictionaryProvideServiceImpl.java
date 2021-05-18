package toy.subscribe.domain.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.dictionary.dto.DictionaryResponseWrapper;
import toy.subscribe.domain.dictionary.repository.DictionaryRepository;
import toy.subscribe.domain.logging.repository.RequestLogRepository;

@Service
@RequiredArgsConstructor
public class DictionaryProvideServiceImpl implements DictionaryProvideService {
    
    private final DictionaryRepository feedBoardRepository;
    private final RequestLogRepository requestLogRepository;
    
    @Override
    @Transactional(readOnly = true)
    public DictionaryResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord) {
        return new DictionaryResponseWrapper(feedBoardRepository.getPageByDictionaries(pageable, enWord, krWord),
                                             requestLogRepository.getCumulativeVisitors(),
                                             requestLogRepository.getDAU());
    }
    
}
