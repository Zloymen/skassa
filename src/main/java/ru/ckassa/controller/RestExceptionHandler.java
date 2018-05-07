package ru.ckassa.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ckassa.error.ErrorDto;
import ru.ckassa.error.TestTaskError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j(topic = "ERRORS")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        log.error("handleException:" , ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get("request_id"),"Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMediaTypeNotAcceptable:" , ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get("request_id"),"HttpMediaTypeNotAcceptable"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TestTaskError.class)
    protected ResponseEntity<Object> handleCustomException(Exception ex) {
        log.error("handleCustomException:" , ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get("request_id"),"Custom error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}