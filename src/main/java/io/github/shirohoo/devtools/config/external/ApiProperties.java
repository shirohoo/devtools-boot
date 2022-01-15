package io.github.shirohoo.devtools.config.external;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApiProperties {
    @Value("${api.key.kakao}")
    private String kakaoKey;
}
