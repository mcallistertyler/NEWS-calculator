package dev.mcallistertyler.news_score_calculator_server.rest;

import dev.mcallistertyler.news_score_calculator_server.domain.NewsErrorResponse;
import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(NewsExceptions.NewsCalculationException.class)
    public ResponseEntity<NewsErrorResponse> handleMissingTypeException(NewsExceptions.NewsCalculationException newsCalculationException) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(HttpStatus.BAD_REQUEST.value(), newsCalculationException.getMessage(), newsCalculationException.getErrorCode());
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NewsExceptions.MissingTypeException.class)
    public ResponseEntity<NewsErrorResponse> handleMissingTypeException(NewsExceptions.MissingTypeException exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrorCode());
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NewsExceptions.MissingValueException.class)
    public ResponseEntity<NewsErrorResponse> handleMissingTypeException(NewsExceptions.MissingValueException exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrorCode());
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<NewsErrorResponse> handleMessageNotReadableException(HttpMessageNotReadableException exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(HttpStatus.BAD_REQUEST.value(), "Received request was unserializable into a valid object", "MALFORMED_REQUEST");
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NewsExceptions.MeasurementRangeAboveMaximum.class)
    public ResponseEntity<NewsErrorResponse> handleIllegalArgumentException(NewsExceptions.MeasurementRangeAboveMaximum exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrorCode());
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NewsExceptions.MeasurementRangeBelowMinimum.class)
    public ResponseEntity<NewsErrorResponse> handleIllegalArgumentException(NewsExceptions.MeasurementRangeBelowMinimum exception) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrorCode());
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<NewsErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String objectName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(objectName, errorMessage);
        });

        System.out.println("Detailed message code " + ex.getDetailMessageCode());
        System.out.println("Detailed message " + Arrays.toString(ex.getDetailMessageArguments()));
        System.out.println("Looking at errors " + errors);

        NewsErrorResponse errorResponse = new NewsErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation of request failed",
                "VALIDATION_ERROR"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
