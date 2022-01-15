package io.github.shirohoo.devtools.config.http.log.repository;

public interface HttpLogCustomRepository {
    Long findDau();
    Long findCumulativeVisitors();
}
