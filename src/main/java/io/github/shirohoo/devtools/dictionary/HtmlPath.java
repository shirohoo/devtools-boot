package io.github.shirohoo.devtools.dictionary;

import lombok.Getter;

@Getter
public enum HtmlPath {
    JUNIT5(System.getProperty("user.home") + "/env/junit5_document.html"),
    HIBERNATE(System.getProperty("user.home") + "/env/hibernate_document.html"),
    SPRING_DATA_JPA(System.getProperty("user.home") + "/env/spring_data_jpa_document.html"),
    SPRING_BOOT(System.getProperty("user.home") + "/env/springboot_document.html"),
    SPRING_SECURITY(System.getProperty("user.home") + "/env/springsecurity_document.html");

    private final String path;

    HtmlPath(String path) {
        this.path = path;
    }
}
