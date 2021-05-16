package toy.subscribe.domain.dictionary.type;

import lombok.Getter;

@Getter
public enum HtmlPath {
    SPRING_BOOT("src/test/resources/springboot_document.html"),
    SPRING_SECURITY("src/test/resources/springsecurity_document.html");
    
    public final String path;
    
    HtmlPath(String path) {
        this.path = path;
    }
}
