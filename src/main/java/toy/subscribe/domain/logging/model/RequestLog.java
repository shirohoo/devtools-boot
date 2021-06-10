package toy.subscribe.domain.logging.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import toy.subscribe.common.BaseTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners({AuditingEntityListener.class})
public class RequestLog extends BaseTime implements Serializable {
    private static final long serialVersionUID = -8594020435254416824L;
    
    @Column(name = "request_log_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientIp;
    private String httpMethod;
    private String requestUri;
}
