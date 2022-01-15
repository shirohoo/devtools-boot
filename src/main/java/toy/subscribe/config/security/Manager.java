package toy.subscribe.config.security;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import toy.subscribe.config.model.BaseEntity;

import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseEntity {
    private String username;
    private String password;
    private String authority;

    @Builder(access = AccessLevel.PUBLIC)
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
