package com.pim.projects.besttravel.api.controller.error_handler;

import com.pim.projects.besttravel.util.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {


    @ExceptionHandler(IdNotFoundException.class)
    public String handleIdNotFoundException(IdNotFoundException exception){
        return exception.getMessage();
    }
}
