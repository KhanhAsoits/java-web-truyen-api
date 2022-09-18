package com.shv.app.comons.validators.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

class HandleResult<E>{
    private String message;
    private String field;
    private E result = null;

    private List<E> results = new ArrayList<>();

    public List<E> getResults() {
        return results;
    }

    public void setResults(List<E> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }
}
@ControllerAdvice
public class HandleController<T> {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponsesErrors onCreateMethod(MethodArgumentNotValidException exception) {
        ResponsesErrors responsesErrors = new ResponsesErrors();
        for (FieldError fieldError : exception.getFieldErrors()) {
            responsesErrors.violations.add(new Violation(fieldError.getDefaultMessage(), fieldError.getField()));
        }
        return responsesErrors;
    }

    protected ResponseEntity<?> InternalServerResponse(Exception exception) {
        HandleResult result = new HandleResult();
        result.setMessage(exception.getMessage());
        return ResponseEntity.internalServerError().body(result);
    }

    protected ResponseEntity<?> ResultResponse(T entity) {
        HandleResult result = new HandleResult();
        result.setMessage("Success!");
        result.setField(entity.getClass().getName());
        result.setResult(entity);
        return ResponseEntity.ok().body(result);
    }
    protected ResponseEntity<?> BadRequest(String message){
        HandleResult result = new HandleResult();
        result.setMessage(message);
        return ResponseEntity.badRequest().body(result);
    }
    protected ResponseEntity<?> ResultListResponse(List<T> list) {
        HandleResult result = new HandleResult();
        result.setMessage("Success!");
        result.setField(list.getClass().getName());
        result.setResults(list);
        return ResponseEntity.ok().body(result);
    }
}
