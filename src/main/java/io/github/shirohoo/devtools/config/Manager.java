package io.github.shirohoo.devtools.config;

import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseEntity {
    private String username;
    private String password;
    private String authority;

    @Builder
    private Manager(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public static Manager of(String username, String password, String authority) {
        return new Manager(username, password, authority);
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.authority));
    }
}
