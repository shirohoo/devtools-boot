package io.github.shirohoo.devtools.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLogInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;
    private final HttpLogRepository httpLogRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logging(request, response);
    }

    private void logging(HttpServletRequest request, HttpServletResponse response) {
        if (request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper")) {
            return;
        }
        httpLogRepository.save(HttpLog.of(request, response, objectMapper));
    }
}
