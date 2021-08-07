package toy.subscribe.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;
import toy.subscribe.configs.http.wrapper.HttpResponseWrapper;
import toy.subscribe.dictionary.dto.DictionaryDto;
import toy.subscribe.dictionary.repository.DictionaryRepository;

@Service
@RequiredArgsConstructor
public class DictionaryProvideService {
    private final DictionaryRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    public HttpResponseWrapper<DictionaryDto> provideDictionaryWrapper(Pageable pageable, String enWord, String krWord) {
        return HttpResponseWrapper.<DictionaryDto>builder()
                                  .pages(feedBoardRepository.findPage(pageable, enWord, krWord))
                                  .visitorsOfDay(requestLogRepository.findCumulativeVisitors())
                                  .visitorsOfReduce(requestLogRepository.findDau())
                                  .build();
    }
}
