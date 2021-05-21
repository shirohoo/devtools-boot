package toy.subscribe.domain.dictionary.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import toy.subscribe.domain.dictionary.model.HtmlPath;

@SpringBootTest
@Disabled("단어장생성_카카오사전_API_일일호출제한_약2,000회")
class DictionaryServiceImplTest {
    
    @Autowired
    DictionaryService dictionaryService;
    
    @Test
    @Rollback(value = false)
    @DisplayName(value = "단어장생성기_실행")
    void create() {
        String html = HtmlPath.SPRING_DATA_JPA.getPath();
    
        dictionaryService.run(html);
    }
    
}