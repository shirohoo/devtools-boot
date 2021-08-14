package toy.subscribe.configs.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import toy.subscribe.configs.exception.PageableLargeSizeException;

import java.util.Iterator;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(SimpleResponse.of(String.valueOf(BAD_REQUEST.value()), getResultMessage(ex)));
    }

    protected String getResultMessage(final MethodArgumentNotValidException ex) {
        final Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (iterator.hasNext() == true) {
            final ObjectError objectError = iterator.next();
            resultMessageBuilder
                    .append("[")
                    .append(getPropertyName(objectError.getCodes()[0]))
                    .append("] ")
                    .append(objectError.getDefaultMessage());

            if (iterator.hasNext() == true) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }

    private String getPropertyName(final String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }

    @ExceptionHandler
    public ResponseEntity<SimpleResponse> pageableLargeSizeHandle(PageableLargeSizeException e) {
        return ResponseEntity.badRequest().body(SimpleResponse.of(String.valueOf(BAD_REQUEST.value()), "Request page size is too large !"));
    }
}

