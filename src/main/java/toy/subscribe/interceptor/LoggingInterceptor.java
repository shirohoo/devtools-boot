package toy.subscribe.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import toy.subscribe.domain.entity.RequestLog;
import toy.subscribe.mvc.repository.RequestLogRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
    
    private final RequestLogRepository requestLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientIp = request.getHeader("X-Forwarded-For");
        String httpMethod = request.getMethod();
        String requestUri = request.getRequestURI();

        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }

        saveRequestLog(clientIp, httpMethod, requestUri);
        
        return true;
    }
    
    private void saveRequestLog(String clientIp, String httpMethod, String requestUri) {
        RequestLog requestLog = RequestLog.builder()
                                          .clientIp(clientIp)
                                          .httpMethod(httpMethod)
                                          .requestUri(requestUri)
                                          .build();
    
        requestLogRepository.save(requestLog);
    }
    
}