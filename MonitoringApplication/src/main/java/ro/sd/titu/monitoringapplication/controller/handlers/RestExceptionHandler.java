package ro.sd.titu.monitoringapplication.controller.handlers;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExpiredJwtException.class, SignatureException.class})
    protected ResponseEntity<Object> handleExceptions(
            RuntimeException e, WebRequest request) {
        return handleException(e.getMessage());
    }

    private ResponseEntity<Object> handleException(String message) {
        ApiException apiException = new ApiException(HttpStatus.FORBIDDEN);
        apiException.setMessage(message);
        apiException.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiException, apiException.getStatus());
    }


}
