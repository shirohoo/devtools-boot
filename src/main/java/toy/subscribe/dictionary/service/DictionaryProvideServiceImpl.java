package toy.subscribe.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.configs.dtos.ResponseWrapper;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;
import toy.subscribe.dictionary.repository.DictionaryRepository;

@Service
@RequiredArgsConstructor
public class DictionaryProvideServiceImpl implements DictionaryProvideService {
    private final DictionaryRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseWrapper provideDictionaryWrapper(Pageable pageable, String enWord, String krWord) {
        return new ResponseWrapper(feedBoardRepository.getPageFromDictionaries(pageable, enWord, krWord),
                                   requestLogRepository.findCumulativeVisitors(),
                                   requestLogRepository.findDau());
    }
}
