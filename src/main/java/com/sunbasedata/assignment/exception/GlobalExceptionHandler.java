package com.sunbasedata.assignment.exception;
import com.sunbasedata.assignment.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotAuthorizedException(NotAuthorizedException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setMessage("Unauthorized! Enter valid token");
        errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler(CustomerCreationException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerCreationException(CustomerCreationException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }
}