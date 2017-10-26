package cz.linksoft.hr.test.core.rest.controller;

import cz.linksoft.hr.test.core.rest.controller.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Ancestor for all controllers.
 *
 * @author jakubchalupa
 * @since 25.10.17
 */
public abstract class AbstractController {

    /**
     * @param e ResourceNotFoundException
     * @return response entity with given response code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseWrapper> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(new ExceptionResponseWrapper("resource not found"), HttpStatus.NOT_FOUND);
    }

    /**
     * @param e IllegalArgumentException
     * @return response entity with given response code
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseWrapper> handleIllegalArgumentException(Exception e) {
        return new ResponseEntity<>(new ExceptionResponseWrapper(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * response for exception
     */
    private class ExceptionResponseWrapper {

        private final String message;

        private ExceptionResponseWrapper(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }


}
