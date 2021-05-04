package toy.subscribe.domain.logging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.subscribe.domain.logging.model.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>,
                                              QuerydslPredicateExecutor<RequestLog>,
                                              RequestLogCustomRepository {
    
}
