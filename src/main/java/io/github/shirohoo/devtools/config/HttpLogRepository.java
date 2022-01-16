package io.github.shirohoo.devtools.config;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogQueryRepository {
}
