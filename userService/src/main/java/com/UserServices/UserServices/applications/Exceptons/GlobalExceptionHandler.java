package com.UserServices.UserServices.applications.Exceptons;

import com.UserServices.UserServices.apis.Dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import com.UserServices.UserServices.apis.Dto.ErrorResponse;
import com.UserServices.UserServices.applications.producer.RequestLoggerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    RequestLoggerProducer requestLoggerProducer;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        ErrorResponse error = new ErrorResponse(

                ex.getHttpStatus().value(),
                ex.getErrorCode(),
                ex.getMessage()
        );
        requestLoggerProducer.log(error.toString(),"response");
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    // Fallback for uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "INTERNAL_ERROR"
        );
        requestLoggerProducer.log(error.toString(),"response");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Validation failed");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);
        requestLoggerProducer.log(errors.toString(),"response");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
