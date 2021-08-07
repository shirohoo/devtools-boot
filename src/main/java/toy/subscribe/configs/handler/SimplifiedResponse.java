package toy.subscribe.configs.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplifiedResponse {
    private String resCode;
    private String resMessage;

    private SimplifiedResponse(String resCode, String resMessage) {
        this.resCode = resCode;
        this.resMessage = resMessage;
    }

    public static SimplifiedResponse of(String resCode, String resMessage) {
        return new SimplifiedResponse(resCode, resMessage);
    }
}
