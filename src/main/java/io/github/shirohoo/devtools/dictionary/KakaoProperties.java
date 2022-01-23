package io.github.shirohoo.devtools.dictionary;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
class KakaoProperties {
    @Value("${api.key.kakao}")
    private String kakaoKey;
}
