package io.github.shirohoo.devtools.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class DictionaryParser {
    private final Translator translator;
    private final DictionaryRepository dictionaryRepository;

    @Transactional
    void parse(String url) {
        HtmlParser parser = HtmlParser.getInstance();
        Set<String> enWords = parser.parse(parser.read(url));
        createDictionary(enWords);
    }

    private void createDictionary(Set<String> enWords) {
        List<Dictionary> dictionaries = new ArrayList<>();
        for (String enWord : enWords) {
            Optional<String> translated = translator.translate(enWord);
            if (translated.isEmpty()) {
                continue;
            }
            String krWord = translated.get();
            if (!dictionaryRepository.existsByEnWord(enWord)) {
                dictionaries.add(Dictionary.of(enWord, krWord));
            }
        }
        dictionaryRepository.saveAll(dictionaries);
    }
}
