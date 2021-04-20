package toy.subscribe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.domain.entity.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

}
