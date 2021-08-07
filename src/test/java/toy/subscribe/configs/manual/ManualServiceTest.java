package toy.subscribe.configs.manual;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled(value = "엑셀 메뉴얼입력기 테스트용도아님 실행&활성화 신중하게")
class ManualServiceTest {
    private final ManualService manualService;

    public ManualServiceTest(final ManualService manualService) {
        this.manualService = manualService;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("엑셀 메뉴얼입력기")
    public void save() throws Exception {
        manualService.save();
    }
}
