package com.test.firstproject.exception;

import com.test.firstproject.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.test.firstproject.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleStudentNotFound(
            StudentNotFoundException exception
    ){
        log.warn(
                "Student not found: {}",
                exception.getMessage()
        );

        ErrorResponse response =
                new ErrorResponse(
                        exception.getMessage(),
                        false,
                        null,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGlobalException(
            Exception exception
    ) {
        ErrorResponse response =
                new ErrorResponse(
                        "something went wrong",
                        false,
                        null,
                        LocalDateTime.now()
                );
        log.error(
                "Unexpected exception occurred.",
                exception
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ){

        Map<String,String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ErrorResponse response =
                new ErrorResponse(
                        "Validation failed",
                        false,
                        errors,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(
            DataIntegrityViolationException exception
    ){

        ErrorResponse response =
                new ErrorResponse(
                        "Database constraint violation",
                        false,
                        null,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);

    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleEmailAlreadyExists(
            EmailAlreadyExistsException exception
    ){


        ErrorResponse response =
                new ErrorResponse(
                        exception.getMessage(),
                        false,
                        null,
                        LocalDateTime.now()
                );

        log.warn(
                "Duplicate email: {}",
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);

    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse>
    handleDatabaseError(
            DataAccessException exception
    ){


        ErrorResponse response =
                new ErrorResponse(
                        "Database error occurred",
                        false,
                        null,
                        LocalDateTime.now()
                );


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleUsernameAlreadyExists(
            UsernameAlreadyExistsException exception
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        "User Name Already Exist",
                        false,
                        null,
                        LocalDateTime.now()

                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);

    }
    @ExceptionHandler(UsernameDoesNotExistException.class)
    public ResponseEntity<ErrorResponse>
    handleUsernameDoesNotExist(
            UsernameDoesNotExistException exception
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        "User Name Dosen't Exist",
                        false,
                        null,
                        LocalDateTime.now()

                );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);

    }

    // Handles bad password, disabled/locked accounts, etc. from AuthenticationManager.authenticate()
    // during login. Without this, these exceptions fall through to handleGlobalException()
    // and incorrectly return 500 instead of 401.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse>
    handleAuthenticationException(
            AuthenticationException exception
    ) {

        log.warn(
                "Authentication failed: {}",
                exception.getMessage()
        );

        // Deliberately generic message: don't reveal whether the username exists,
        // was locked, or the password was wrong.
        String message = "Invalid username or password";

        ErrorResponse response =
                new ErrorResponse(
                        message,
                        false,
                        null,
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);

    }
    @ExceptionHandler(ExternalApiException.class)

    public ResponseEntity<ApiResponse<Object>>
    handleExternalApiException(
            ExternalApiException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(

                        ex.getMessage(),

                        ex.getResponseCode(),

                        null

                );

        return ResponseEntity

                .status(HttpStatus.BAD_GATEWAY)

                .body(response);

    }
}