package com.pim.projects.besttravel.api.controller.error_handler;

import com.pim.projects.besttravel.api.model.responses.BaseErrorResponse;
import com.pim.projects.besttravel.api.model.responses.ErrorResponse;
import com.pim.projects.besttravel.api.model.responses.ErrorsResponse;
import com.pim.projects.besttravel.util.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFoundException(IdNotFoundException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleIdNotFoundException(MethodArgumentNotValidException exception) {
        var errors = new ArrayList<String>();

        exception.getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorsResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();

    }
}
