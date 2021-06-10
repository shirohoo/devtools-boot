package toy.subscribe.common.manual;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled(value = "엑셀_메뉴얼입력기_테스트용도아님_실행&활성화_신중하게")
class ManualServiceTest {
    @Autowired ManualService manualService;
    
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("엑셀_메뉴얼입력기")
    public void save() throws Exception {
        manualService.save();
    }
}
