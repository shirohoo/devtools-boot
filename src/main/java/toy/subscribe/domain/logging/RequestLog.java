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
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_LOG_ID")
    private Long id;
    
    @Column(name = "CLIENT_IP")
    private String clientIp;
    
    @Column(name = "HTTP_METHOD")
    private String httpMethod;
    
    @Column(name = "REQUEST_URI")
    private String requestUri;
    
}
