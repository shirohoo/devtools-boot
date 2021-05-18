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
    
    private DocumentParser parser = new DocumentParser();
    
    @Override
    @Transactional
    public void create(String htmlPath) {
        String html = parser.read(htmlPath);
        Set<String> enWords = parser.parsing(html);
        
        if(enWords == null) {
            log.warn("Could not make a dictionary! enWords is null. ");
            return;
        }
        
        List<Dictionary> dictionaries = new ArrayList<>();
        
        for(String enWord : enWords) {
            String krWord = translator.translate(enWord)
                                      .orElseThrow(()->new NoSuchElementException("단어를 찾지 못했습니다"));
            
            String imgUri = String.format("https://www.google.com/search?q=%s&" +
                                          "newwindow=1&source=lnms&tbm=isch&sa=X" +
                                          "&ved=2ahUKEwjwgc7Lj83wAhXHFogKHYUABiYQ_" +
                                          "AUoAXoECAEQAw&biw=1920&bih=937", enWord);
            
            Dictionary dictionary = Dictionary.builder().enWord(enWord)
                                              .krWord(krWord)
                                              .build();
            
            dictionaries.add(dictionary);
        }
        dictionaryRepository.saveAll(dictionaries);
    }
    
}
