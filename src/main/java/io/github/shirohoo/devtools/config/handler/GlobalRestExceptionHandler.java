package io.github.shirohoo.devtools.config.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.github.shirohoo.devtools.config.exception.PageableLargeSizeException;

import java.util.Iterator;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(SimpleHttpResponse.of(BAD_REQUEST, getResultMessage(ex))
                );
    }

    protected String getResultMessage(final MethodArgumentNotValidException ex) {
        final Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            final ObjectError objectError = iterator.next();
            resultMessageBuilder
                    .append("[")
                    .append(getPropertyName(objectError.getCodes()[0]))
                    .append("] ")
                    .append(objectError.getDefaultMessage());

            if (iterator.hasNext()) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }

    private String getPropertyName(final String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }

    @ExceptionHandler
    public ResponseEntity<SimpleHttpResponse> pageableLargeSizeHandle(final PageableLargeSizeException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(SimpleHttpResponse.of(BAD_REQUEST, e.getMessage())
                );
    }
}

