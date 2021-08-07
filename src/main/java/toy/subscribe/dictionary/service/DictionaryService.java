package toy.subscribe.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.dictionary.model.Dictionary;
import toy.subscribe.dictionary.parser.DocumentParser;
import toy.subscribe.dictionary.parser.Translator;
import toy.subscribe.dictionary.repository.DictionaryRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final Translator translator;
    private final DictionaryRepository dictionaryRepository;

    @Transactional
    public void parse(String html) {
        final DocumentParser parser = DocumentParser.getInstance();
        final Set<String> enWords = parser.parsing(parser.read(html));

        if (Objects.isNull(enWords)) {
            log.warn("Could not make a dictionary! english words is null.");
            return;
        }
        createDictionary(parser.filtering(enWords));
    }

    private void createDictionary(Set<String> set) {
        List<Dictionary> dictionaries = new ArrayList<>();
        for (String enWord : set) {
            String krWord = translator.translate(enWord)
                                      .orElseThrow(() -> new NoSuchElementException("단어를 찾지 못했습니다."));

            Dictionary dictionary = Dictionary.of(enWord, krWord);

            if (!dictionaryRepository.existsByEnWord(enWord)) {
                dictionaries.add(dictionary);
            }
        }
        dictionaryRepository.saveAll(dictionaries);
    }
}
