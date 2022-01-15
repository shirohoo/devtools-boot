package io.github.shirohoo.devtools.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Company {
    private String key;
    private String name;
    private String imgPath;

    static Company of(String key, String name, String imgPath) {
        return new Company(key, name, imgPath);
    }
}
