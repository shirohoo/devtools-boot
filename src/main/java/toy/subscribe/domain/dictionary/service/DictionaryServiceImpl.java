package toy.subscribe.domain.dictionary.service;

import lombok.RequiredArgsConstructor;
import toy.subscribe.domain.dictionary.parser.DocumentParser;
import toy.subscribe.domain.dictionary.parser.Translator;
import toy.subscribe.domain.dictionary.type.HtmlPath;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    
    private final DocumentParser parser;
    private final Translator translator;
    
    @Override
    public void create() throws IOException {
        String html = parser.read(HtmlPath.SPRING_BOOT.getPath());
        Set<String> parsing = parser.parsing(html);
        
        StringBuilder sb = new StringBuilder();
        for(String s : parsing) {
            String word = translator.translate(s)
                                    .orElseThrow(()->new NoSuchElementException("단어를 찾지 못했습니다"));
            
            sb.append(s).append(" = ").append(word)
              .append(" / ")
              .append("이미지").append(" = ").append(String.format("https://www.google.com/search?q=%s&newwindow=1&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjwgc7Lj83wAhXHFogKHYUABiYQ_AUoAXoECAEQAw&biw=1920&bih=937", s))
              .append("\n");
        }
        
        // TODO
        System.out.println(sb);
    }
    
}
