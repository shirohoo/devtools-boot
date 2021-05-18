package toy.subscribe.domain.dictionary.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.dictionary.type.HtmlPath;

@SpringBootTest
@Disabled(value = "단어장생성기_테스트용도아님_실행&활성화_신중하게")
class DictionaryServiceImplTest {
    
    @Autowired
    DictionaryService dictionaryService;
    
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("단어장생성_카카오사전_API_일일호출제한_약2,000회")
    void create() {
        String htmlPath = HtmlPath.SPRING_SECURITY.getPath();
        
        dictionaryService.create(htmlPath);
    }
    
}