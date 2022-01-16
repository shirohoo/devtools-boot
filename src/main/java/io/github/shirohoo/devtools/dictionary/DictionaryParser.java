package io.github.shirohoo.devtools.dictionary;

import java.util.ArrayList;
import java.util.List;
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
    void parse(String htmlPath) {
        DocumentParser parser = DocumentParser.getInstance();
        Set<String> enWords = parser.parsing(parser.read(htmlPath));

        if (enWords == null) {
            log.warn("Could not make a dictionary! english words is null.");
            return;
        }
        createDictionary(parser.filtering(enWords));
    }

    private void createDictionary(Set<String> set) {
        List<Dictionary> dictionaries = new ArrayList<>();
        for (String enWord : set) {
            String krWord = translator.translate(enWord)
                .orElseThrow();

            Dictionary dictionary = Dictionary.of(enWord, krWord);

            if (!dictionaryRepository.existsByEnWord(enWord)) {
                dictionaries.add(dictionary);
            }
        }
        dictionaryRepository.saveAll(dictionaries);
    }
}
