package toy.subscribe.configs.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import toy.subscribe.configs.http.log.model.HttpLog;
import toy.subscribe.configs.http.log.repository.HttpLogRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLogInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;
    private final HttpLogRepository httpLogRepository;

    @Override
    @Transactional
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        parse(request, response);
    }

    private void parse(HttpServletRequest request, HttpServletResponse response) {
        if (request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper")) {
            return;
        }
        httpLogRepository.save(HttpLog.of(request, response, objectMapper));
    }
}
