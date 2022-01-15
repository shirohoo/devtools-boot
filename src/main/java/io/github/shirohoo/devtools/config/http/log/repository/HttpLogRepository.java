package io.github.shirohoo.devtools.config.http.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.shirohoo.devtools.config.http.log.model.HttpLog;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogCustomRepository {
}
