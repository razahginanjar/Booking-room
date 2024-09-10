package com.enigma.challengebookingroom.exception;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.stream.Collectors;

import com.enigma.challengebookingroom.dto.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.challengebookingroom.constant.ConstantMessage;
import com.enigma.challengebookingroom.dto.response.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class AppExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CommonResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error(ConstantMessage.NOT_FOUND, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        CommonResponse.<String>builder()
                                .data(ConstantMessage.NOT_FOUND)
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse<String>> handleIOException(IOException ex) {
        logger.error(ConstantMessage.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<String>builder()
                                .data(ConstantMessage.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse<String>>  handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ConstantMessage.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<String>builder()
                                .data(ConstantMessage.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<String>> handleResponseStatusException(ResponseStatusException ex) {
        logger.error(ex.getLocalizedMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(
                        CommonResponse.<String>builder()
                                .data(ex.getLocalizedMessage())
                                .statusCode(ex.getStatusCode().value())
                                .message("Error {}:")
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error(ConstantMessage.BAD_REQUEST, ex);

        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(errorMessage);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<ErrorResponse>builder()
                                .data(errorResponse)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<CommonResponse<String>> handleGenericException(Exception ex) {
//        logger.error(ConstantMessage.INTERNAL_SERVER_ERROR, ex);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(
//                        CommonResponse.<String>builder()
//                                .data(ConstantMessage.INTERNAL_SERVER_ERROR)
//                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                                .message(ex.getLocalizedMessage())
//                                .build()
//                );
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse<ErrorResponse>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ConstantMessage.FORBIDDEN + ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        CommonResponse.<ErrorResponse>builder()
                                .data(errorResponse)
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<CommonResponse<String>> dateTimeException(DateTimeException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<String>builder()
                                .data(ConstantMessage.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(exception.getLocalizedMessage())
                                .build()
                );
    }
}
