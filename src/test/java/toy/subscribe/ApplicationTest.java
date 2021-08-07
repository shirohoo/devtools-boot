package toy.subscribe;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationTest {
    @Test
    @DisplayName("스프링부트 컨텍스트가 제대로 올라가는지 테스트한다")
    @Disabled(value = "테스트 시간이 너무 오래 걸려 비활성화")
    public void main() {
        Application.main(new String[]{});
    }
}
