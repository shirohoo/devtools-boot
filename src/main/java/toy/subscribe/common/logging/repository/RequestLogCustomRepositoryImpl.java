package toy.subscribe.common.logging.repository;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.common.logging.model.RequestLog;

import java.time.LocalDateTime;

import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import static toy.subscribe.common.logging.model.QRequestLog.requestLog;

public class RequestLogCustomRepositoryImpl extends QuerydslRepositorySupport implements RequestLogCustomRepository {
    public RequestLogCustomRepositoryImpl() {
        super(RequestLog.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getDAU() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory
                .select(requestLog.clientIp.countDistinct())
                .from(requestLog)
                .where(requestLog.regDate.gt(onTime()))
                .fetchOne();
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getCumulativeVisitors() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory
                .select(requestLog.clientIp.countDistinct())
                .from(requestLog)
                .groupBy(date(requestLog.regDate))
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
