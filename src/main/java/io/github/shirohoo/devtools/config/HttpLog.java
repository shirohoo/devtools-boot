package io.github.shirohoo.devtools.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HttpLog extends BaseEntity {
    private String clientIp;
    private String httpMethod;
    private String requestUri;
    private String requestBody;
    private String responseBody;
    private String token;
    private int httpStatusCode;

    @Builder(access = AccessLevel.PUBLIC)
    private HttpLog(String clientIp, String httpMethod, String requestUri, String requestBody, String responseBody, String token, Integer httpStatusCode) {
        this.clientIp = clientIp;
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.token = token;
        this.httpStatusCode = httpStatusCode;
    }

    public static HttpLog of(String httpMethod, String uri, String requestBody, String ip, String token, Integer httpStatusCode) {
        return of(httpMethod, uri, requestBody, null, ip, token, httpStatusCode);
    }

    public static HttpLog of(String httpMethod, String uri, String requestBody, String responseBody, String ip, String token, Integer httpStatusCode) {
        return new HttpLog(ip, httpMethod, uri, requestBody, responseBody, token, httpStatusCode);
    }

    public static HttpLog of(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {
        return getResponseBody(response, objectMapper)
            .map(responseBody -> HttpLog.builder()
                .httpMethod(request.getMethod())
                .requestUri(request.getRequestURI())
                .requestBody(getRequestBody(request, objectMapper))
                .responseBody(responseBody.toString())
                .clientIp(getClientIp(request))
                .token(getToken(request))
                .httpStatusCode(response.getStatus())
                .build()
            )
            .orElse(HttpLog.builder()
                .httpMethod(request.getMethod())
                .requestUri(request.getRequestURI())
                .requestBody(getRequestBody(request, objectMapper))
                .clientIp(getClientIp(request))
                .token(getToken(request))
                .httpStatusCode(response.getStatus())
                .build());
    }

    private static Optional<JsonNode> getResponseBody(HttpServletResponse response, ObjectMapper objectMapper) {
        ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        if (isReadableResponse(cachingResponse)) {
            return readTree(objectMapper, cachingResponse);
        }
        return Optional.empty();
    }

    private static boolean isReadableResponse(ContentCachingResponseWrapper cachingResponse) {
        return cachingResponse.getContentType() != null && isJson(cachingResponse.getContentType()) && cachingResponse.getContentAsByteArray().length != 0;
    }

    private static Optional<JsonNode> readTree(ObjectMapper objectMapper, ContentCachingResponseWrapper cachingResponse) {
        try {
            return Optional.of(objectMapper.readTree(cachingResponse.getContentAsByteArray()));
        } catch (IOException e) {
            log.warn("ContentCachingResponseWrapper parse error! returns null. info : {}", e.getMessage());
            return Optional.empty();
        }
    }

    private static String getRequestBody(HttpServletRequest request, ObjectMapper objectMapper) {
        ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        if (isReadableRequest(cachingRequest)) {
            return readTree(objectMapper, cachingRequest);
        }
        return "";
    }

    private static boolean isReadableRequest(ContentCachingRequestWrapper cachingRequest) {
        return cachingRequest.getContentType() != null && isJson(cachingRequest.getContentType()) && cachingRequest.getContentAsByteArray().length != 0;
    }

    private static boolean isJson(String contentType) {
        return contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    private static String readTree(ObjectMapper objectMapper, ContentCachingRequestWrapper cachingRequest) {
        try {
            JsonNode jsonNode = objectMapper.readTree(cachingRequest.getContentAsByteArray());
            removeSecurityInformation(jsonNode);
            return jsonNode.toString();
        } catch (IOException e) {
            log.warn("ContentCachingRequestWrapper parse error! returns null. info : {}", e.getMessage());
            return "";
        }
    }

    private static void removeSecurityInformation(JsonNode jsonNode) {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            if (fields.next().toString().contains("password")) {
                fields.remove();
            }
        }
    }

    private static String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
