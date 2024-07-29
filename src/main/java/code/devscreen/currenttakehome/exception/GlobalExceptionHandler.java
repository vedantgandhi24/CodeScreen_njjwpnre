package code.devscreen.currenttakehome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import code.devscreen.currenttakehome.DTO.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandleTransactionAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleTransactionAlreadyExistsException(HandleTransactionAlreadyExistsException ex) {

        ErrorDTO message = new ErrorDTO();
        message.setCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        message.setMessage(ex.getMessage());    
        return new ResponseEntity<ErrorDTO>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandleInsufficientBalanceException.class)
    public ResponseEntity<ErrorDTO> handleInsufficientBalanceException(HandleInsufficientBalanceException ex) {

        ErrorDTO message = new ErrorDTO();
        message.setCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        message.setMessage(ex.getMessage());    
        return new ResponseEntity<ErrorDTO>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> globalExceptionHandler(Exception ex) {

        ErrorDTO message = new ErrorDTO();
        message.setCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        message.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorDTO>(message, HttpStatus.INTERNAL_SERVER_ERROR);       
  }
}
