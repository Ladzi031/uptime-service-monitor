package za.ac.ladzani.uptimeMonitor.controllers.integrations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import za.ac.ladzani.uptimeMonitor.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class ApiErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException() {
        return null;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return null;
    }
}
