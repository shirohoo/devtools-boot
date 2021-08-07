package toy.subscribe.configs.http.log.repository;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.configs.http.log.model.HttpLog;

import java.time.LocalDateTime;

import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import static toy.subscribe.configs.http.log.model.QHttpLog.httpLog;

public class HttpLogCustomRepositoryImpl extends QuerydslRepositorySupport implements HttpLogCustomRepository {
    public HttpLogCustomRepositoryImpl() {
        super(HttpLog.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findDau() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory
                .select(httpLog.clientIp.countDistinct())
                .from(httpLog)
                .where(httpLog.regDate.gt(onTime()))
                .fetchOne();

    }

    @Override
    @Transactional(readOnly = true)
    public Long findCumulativeVisitors() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory
                .select(httpLog.clientIp.countDistinct())
                .from(httpLog)
                .groupBy(date(httpLog.regDate))
                .fetch()
                .stream()
                .reduce(0L, Long::sum);
    }

    private StringTemplate date(DateTimePath regDate) {
        return stringTemplate("date({0})", regDate);
    }

    private LocalDateTime onTime() {
        return LocalDateTime.of(LocalDateTime.now().getYear(),
                                LocalDateTime.now().getMonth(),
                                LocalDateTime.now().getDayOfMonth(),
                                0, 0, 0);
    }
}
