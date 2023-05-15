package com.pim.projects.besttravel.api.controller.error_handler;

import com.pim.projects.besttravel.api.model.responses.BaseErrorResponse;
import com.pim.projects.besttravel.api.model.responses.ErrorResponse;
import com.pim.projects.besttravel.util.exception.ForbiddenCustomerException;
import com.pim.projects.besttravel.util.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleIdNotFoundException(ForbiddenCustomerException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .errorCode(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
