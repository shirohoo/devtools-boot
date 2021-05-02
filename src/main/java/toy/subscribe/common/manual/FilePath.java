package toy.subscribe.common.manual;

import lombok.Getter;

@Getter
public enum FilePath {
    WOOWABROS("src/test/resources/woowa_tech.xlsx", "/images/woowabros.png"),
    TOSS("src/test/resources/toss_tech.xlsx", "/images/toss.png");
    
    public final String XLSX;
    public final String IMG;
    
    FilePath(String XLSX, String IMG) {
        this.XLSX = XLSX;
        this.IMG = IMG;
    }
}
