package toy.subscribe.configs.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String contentType = request.getHeader("Content-Type");
        if (Objects.nonNull(contentType) && contentType.toLowerCase().contains("multipart/form-data")) {
            filterChain.doFilter(request, response);
        }
        else {
            ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
        }
    }
}
