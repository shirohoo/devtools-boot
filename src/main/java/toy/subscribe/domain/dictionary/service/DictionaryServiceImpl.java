package toy.subscribe.domain.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.dictionary.model.Dictionary;
import toy.subscribe.domain.dictionary.parser.DocumentParser;
import toy.subscribe.domain.dictionary.parser.Translator;
import toy.subscribe.domain.dictionary.repository.DictionaryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    private final Translator translator;
    private final DictionaryRepository dictionaryRepository;

    @Override
    @Transactional
    public void run(String html) {
        DocumentParser parser = new DocumentParser();

        String content = parser.read(html);
        Set<String> enWords = parser.parsing(content);

        if (enWords == null) {
            log.warn("Could not make a dictionary! english words is null.");
            return;
        }
        else {
            createDictionary(parser.filtering(enWords));
        }
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
