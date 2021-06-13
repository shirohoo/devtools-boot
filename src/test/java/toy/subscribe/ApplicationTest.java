package toy.subscribe;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class ApplicationTest {
    @Test
    @DisplayName("스프링부트_메인메소드")
    @Disabled(value = "테스트_시간이_너무_오래_걸려_비활성화_함")
    public void main() {
        Application.main(new String[] {});
    }
}
