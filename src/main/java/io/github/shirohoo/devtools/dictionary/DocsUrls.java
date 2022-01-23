package io.github.shirohoo.devtools.dictionary;

import lombok.Getter;

@Getter
enum DocsUrls {
    JUNIT5("https://junit.org/junit5/docs/current/user-guide/"),
    HIBERNATE("https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html"),
    SPRING_DATA_JPA("https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference"),
    SPRING_BOOT("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"),
    SPRING_SECURITY("https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/htmlsingle/");

    private final String docsUrl;

    DocsUrls(String docsUrl) {
        this.docsUrl = docsUrl;
    }
}
