package toy.subscribe.config.http.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.subscribe.config.http.log.model.HttpLog;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogCustomRepository {
}
