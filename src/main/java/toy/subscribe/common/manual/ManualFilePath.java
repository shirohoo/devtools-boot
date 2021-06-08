package toy.subscribe.common.manual;

import lombok.Getter;

@Getter
public enum ManualFilePath {
    WOOWABROS("src/test/resources/woowa_tech.xlsx", "/images/woowabros.png");
    
    public final String XLSX;
    public final String IMG;
    
    ManualFilePath(String XLSX, String IMG) {
        this.XLSX = XLSX;
        this.IMG = IMG;
    }
}
