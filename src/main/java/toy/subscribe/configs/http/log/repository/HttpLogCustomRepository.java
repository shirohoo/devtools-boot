package toy.subscribe.configs.http.log.repository;

public interface HttpLogCustomRepository {
    Long findDau();

    Long findCumulativeVisitors();
}
