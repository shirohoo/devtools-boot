package io.github.shirohoo.devtools.dictionary;

import io.github.shirohoo.devtools.common.ContentProvider;
import io.github.shirohoo.devtools.config.HttpLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DictionaryProvider implements ContentProvider<Dictionary> {
    private final HttpLogRepository httpLogRepository;
    private final DictionaryRepository dictionaryRepository;

    @Override
    public Page<Dictionary> provide(Pageable pageable, String enWord, String krWord) {
        return dictionaryRepository.findPages(pageable, enWord, krWord);
    }

    @Override
    public Long visitorsOfDay() {
        return httpLogRepository.findVisitorsOfDay();
    }

    @Override
    public Long visitorsOfTotal() {
        return httpLogRepository.findVisitorsOfTotal();
    }
}
