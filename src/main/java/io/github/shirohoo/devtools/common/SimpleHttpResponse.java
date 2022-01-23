package io.github.shirohoo.devtools.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleHttpResponse {
    private String resCode;
    private String resMsg;

    private SimpleHttpResponse(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public static SimpleHttpResponse of(String resCode, String resMsg) {
        return new SimpleHttpResponse(resCode, resMsg);
    }

    public static SimpleHttpResponse of(HttpStatus status, String resMsg) {
        return new SimpleHttpResponse(String.valueOf(status.value()), resMsg);
    }
}
