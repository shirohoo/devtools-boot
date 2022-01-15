package io.github.shirohoo.devtools.dictionary.service;

import io.github.shirohoo.devtools.config.http.log.repository.HttpLogRepository;
import io.github.shirohoo.devtools.common.DevtoolsResponse;
import io.github.shirohoo.devtools.dictionary.dto.DictionaryDto;
import io.github.shirohoo.devtools.dictionary.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DictionaryProvideService {
    private final DictionaryRepository feedBoardRepository;
    private final HttpLogRepository requestLogRepository;

    @Transactional(readOnly = true)
    public DevtoolsResponse<DictionaryDto> provideDictionaryWrapper(Pageable pageable, String enWord, String krWord) {
        return DevtoolsResponse.<DictionaryDto>builder()
            .pages(feedBoardRepository.findPage(pageable, enWord, krWord))
            .visitorsOfDay(requestLogRepository.findDau())
            .visitorsOfTotal(requestLogRepository.findCumulativeVisitors())
            .build();
    }
}
