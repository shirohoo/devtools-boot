package toy.subscribe.blog.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    private String key;
    private String name;
    private String imgPath;

    private Company(final String key, final String name, final String imgPath) {
        this.key = key;
        this.name = name;
        this.imgPath = imgPath;
    }

    @Builder
    public static Company of(final String key, final String name, final String imgPath) {
        return new Company(key, name, imgPath);
    }
}
