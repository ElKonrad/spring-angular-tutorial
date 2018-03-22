package pl.pollub.controller.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.pollub.exception.CommonException;
import pl.pollub.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> handleCommonException(CommonException ex, WebRequest request) {

        return handleExceptionInternal(ex, ErrorResponse.builder().message(ex.getMessage()).build(),
                new HttpHeaders(), ex.getHttpReturnStatus(), request);
    }
}
