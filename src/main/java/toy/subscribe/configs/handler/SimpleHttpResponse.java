package toy.subscribe.configs.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
