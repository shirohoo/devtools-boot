package toy.subscribe.domain.logging;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import toy.subscribe.common.BaseTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners({AuditingEntityListener.class})
public class RequestLog extends BaseTime implements Serializable {
    
    @Column(name = "request_log_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String clientIp;
    
    private String httpMethod;
    
    private String requestUri;
    
}
