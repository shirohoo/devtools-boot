package io.github.shirohoo.devtools.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.shirohoo.devtools.config.http.log.repository.HttpLogRepository;
import io.github.shirohoo.devtools.config.http.wrapper.HttpResponseWrapper;
import io.github.shirohoo.devtools.dictionary.dto.DictionaryDto;
import io.github.shirohoo.devtools.dictionary.repository.DictionaryRepository;

@Service
@RequiredArgsConstructor
public class DictionaryProvideService {
    private final DictionaryRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    public HttpResponseWrapper<DictionaryDto> provideDictionaryWrapper(Pageable pageable, String enWord, String krWord) {
        return HttpResponseWrapper.<DictionaryDto>builder()
                                  .pages(feedBoardRepository.findPage(pageable, enWord, krWord))
                                  .visitorsOfDay(requestLogRepository.findDau())
                                  .visitorsOfReduce(requestLogRepository.findCumulativeVisitors())
                                  .build();
    }
}
