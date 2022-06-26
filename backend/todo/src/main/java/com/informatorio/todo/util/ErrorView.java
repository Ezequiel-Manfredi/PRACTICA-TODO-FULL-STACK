package com.informatorio.todo.util;

import org.springframework.stereotype.Component;

@Component
public class ErrorView {
    
    private String error;

    public String getError() {return error;}

    public ErrorView() {}
    public ErrorView(String error) {
        this.error = error;
    }
}
