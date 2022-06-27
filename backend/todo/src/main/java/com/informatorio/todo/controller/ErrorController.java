package com.informatorio.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.informatorio.todo.error.BoardNotCreatedException;
import com.informatorio.todo.error.BoardNotFoundException;
import com.informatorio.todo.error.TaskNotFoundException;
import com.informatorio.todo.util.ErrorView;

@RestControllerAdvice
public class ErrorController {
    
    @ResponseBody
    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorView boardNotFound(BoardNotFoundException err){
        return new ErrorView(err.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BoardNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorView boardNotCreated(BoardNotCreatedException err){
        return new ErrorView(err.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorView taskNotFound(TaskNotFoundException err){
        return new ErrorView(err.getMessage());
    }
    
}
