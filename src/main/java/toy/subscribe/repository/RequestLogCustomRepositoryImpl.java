package toy.subscribe.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.entity.RequestLog;

import java.time.LocalDateTime;

import static toy.subscribe.domain.entity.QRequestLog.requestLog;

public class RequestLogCustomRepositoryImpl extends QuerydslRepositorySupport implements RequestLogCustomRepository {
    
    public RequestLogCustomRepositoryImpl() {
        super(RequestLog.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getDau() {
    
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
    
        return queryFactory
                .select(requestLog.clientIp.countDistinct())
                .from(requestLog)
                .where(requestLog.regDate.gt(getOnTime()))
                .fetchOne();
    
    }
    
    private LocalDateTime getOnTime() {
        return LocalDateTime.of(LocalDateTime.now().getYear(),
                                LocalDateTime.now().getMonth(),
                                LocalDateTime.now().getDayOfMonth(),
                                0, 0, 0);
    }
    
}
