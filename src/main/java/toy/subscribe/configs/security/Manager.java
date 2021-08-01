package toy.subscribe.configs.security;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.subscribe.configs.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;
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
}
