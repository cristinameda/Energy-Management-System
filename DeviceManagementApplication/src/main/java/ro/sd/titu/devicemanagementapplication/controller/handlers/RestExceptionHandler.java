package ro.sd.titu.devicemanagementapplication.controller.handlers;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.sd.titu.devicemanagementapplication.controller.handlers.exceptions.model.CustomException;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST);
        apiException.setMessage(e.getMessage());
        apiException.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

    @Override
    @Nullable
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatusCode status,
                                                               @NonNull WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST);
        ex.getBindingResult().getFieldErrors().forEach((error) -> insertErrorMessages(apiException, error));

        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

    private void insertErrorMessages(ApiException apiException, FieldError errorField) {
        String errorMessage = errorField.getField() + " " + errorField.getDefaultMessage();
        apiException.setMessage(errorMessage);
        apiException.setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomExceptions(CustomException ex,
                                                            WebRequest request) {
        ApiException apiException = new ApiException(ex.getStatus(), ex.getMessage());
        apiException.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
