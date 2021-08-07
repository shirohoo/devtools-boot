package toy.subscribe.configs.http.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import toy.subscribe.configs.http.log.model.HttpLog;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, QuerydslPredicateExecutor<HttpLog>, HttpLogCustomRepository {
}
