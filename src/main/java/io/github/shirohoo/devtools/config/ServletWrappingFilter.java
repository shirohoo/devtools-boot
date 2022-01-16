package io.github.shirohoo.devtools.config;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (contentType != null && contentType.toLowerCase().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            filterChain.doFilter(request, response);
        } else {
            ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
        }
    }
}
