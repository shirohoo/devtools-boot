package toy.subscribe.configs.external;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource(value = "file:${user.home}/env/dictionary/dictionary.properties")
public class ApiProperties {
    @Value("${api.key.kakao}")
    private String kakaoKey;
}
