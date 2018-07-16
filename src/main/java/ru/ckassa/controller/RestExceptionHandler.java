package ru.ckassa.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ckassa.error.ErrorDto;
import ru.ckassa.error.TestTaskError;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@PropertySource( "classpath:application.properties")
@Slf4j(topic = "ERRORS")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${test-task.log-id}")
    private String logId;


    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleCustomException(DateTimeParseException ex) {
        log.error("DateTimeParseException:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId),  "DateTimeParseException"), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMediaTypeNotAcceptable:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId), "HttpMediaTypeNotAcceptable"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TestTaskError.class)
    protected ResponseEntity<Object> handleCustomException(TestTaskError ex) {
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId),  ex.getErrorEnum().getTitle()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleException:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get("request_id"), "Bad param request"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleException:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId), "Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("handleException:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId), "Router is not exists"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotReadable:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotWritable:", ex);
        return new ResponseEntity<>(new ErrorDto(MDC.get(logId), "Not write response"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleHttpMessageNotWritable:", ex);


        List<ObjectError> list = ex.getBindingResult().getAllErrors();
        String answer = list.stream().map(item ->{
            if(item instanceof FieldError) return "Column " + ((FieldError) item).getField() + ", " + item.getDefaultMessage();
            return item.getDefaultMessage();
        }).collect(Collectors.joining(", "));


        return new ResponseEntity<>(new ErrorDto(MDC.get(logId), answer), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}