package toy.subscribe.domain.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners({AuditingEntityListener.class})
public class RequestLog extends BaseTime {
    
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
