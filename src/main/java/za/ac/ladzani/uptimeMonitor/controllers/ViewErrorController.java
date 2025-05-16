package za.ac.ladzani.uptimeMonitor.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import za.ac.ladzani.uptimeMonitor.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ViewErrorController {

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return null;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(EntityNotFoundException ex, Model page) {
        return null;
    }
}
