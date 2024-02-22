package gloomme.crater365.correspondence;

import gloomme.crater365.correspondence.exception.CorrespondenceDataException;
import gloomme.crater365.correspondence.exception.CorrespondenceNotFoundException;
import gloomme.crater365.correspondence.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/** Handles the exceptions globally in this microservice */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CorrespondenceDataException.class, RuntimeException.class})
    public ResponseEntity<ErrorDetails> handleAsBadRequest(RuntimeException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CorrespondenceNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleAsNotFound(RuntimeException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
