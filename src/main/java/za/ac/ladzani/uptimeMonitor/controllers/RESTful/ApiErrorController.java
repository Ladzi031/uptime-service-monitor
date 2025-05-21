package za.ac.ladzani.uptimeMonitor.controllers.RESTful;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import za.ac.ladzani.uptimeMonitor.exceptions.EntityNotFoundException;

import java.util.logging.Logger;

@RestControllerAdvice
public class ApiErrorController {
    private final Logger logger = Logger.getLogger(ApiErrorController.class.getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        logger.severe(ex.getMessage()+ex.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.info(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());

    }

}
