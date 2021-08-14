package toy.subscribe.configs.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleResponse {
    private String resCode;
    private String resMessage;

    private SimpleResponse(String resCode, String resMessage) {
        this.resCode = resCode;
        this.resMessage = resMessage;
    }

    public static SimpleResponse of(String resCode, String resMessage) {
        return new SimpleResponse(resCode, resMessage);
    }
}
