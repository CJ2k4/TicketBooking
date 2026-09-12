package com.TicketBooking.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(SeatUnavailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleSeatUnavailableException(SeatUnavailableException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(InvalidSeatsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidSeatsException(InvalidSeatsException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HoldExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ErrorResponse handleHoldExpiredException(HoldExpiredException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse onConcurrentUpdate(ObjectOptimisticLockingFailureException exception){
        return new ErrorResponse("Hold was modified concurrently, please retry");
    }

    @ExceptionHandler(HoldNotConfirmableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleHoldNotConfirmableException(HoldNotConfirmableException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onValidationError(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ErrorResponse(msg);
    }
}
