package toy.subscribe.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.subscribe.domain.entity.FeedBoard;
import toy.subscribe.domain.entity.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>, QuerydslPredicateExecutor<FeedBoard>, RequestLogCustomRepository {

}
