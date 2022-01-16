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

    private SimpleHttpResponse(final String resCode, final String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public static SimpleHttpResponse of(final String resCode, final String resMsg) {
        return new SimpleHttpResponse(resCode, resMsg);
    }

    public static SimpleHttpResponse of(final HttpStatus status, final String resMsg) {
        return new SimpleHttpResponse(String.valueOf(status.value()), resMsg);
    }
}
