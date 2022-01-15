package toy.subscribe.config.manual;

import lombok.Getter;

@Getter
public enum ManualFilePath {
    WOOWABROS("src/test/resources/woowa_tech.xlsx", "/images/woowabros.png");

    private final String XLSX;
    private final String IMG;

    ManualFilePath(String XLSX, String IMG) {
        this.XLSX = XLSX;
        this.IMG = IMG;
    }
}
