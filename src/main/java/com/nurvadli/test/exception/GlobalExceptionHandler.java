package com.nurvadli.test.exception;

import com.nurvadli.test.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<ResponseDto> handleBadRequest(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        ex.printStackTrace();

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(ResponseDto.builder().status(status.name())
                .code(30001)
                .message("")
                .build(), status);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDto> handleArgumentNotValid(MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult().getFieldErrors().stream().findFirst().get().getDefaultMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(ResponseDto.builder().status(status.name())
                .code(30001)
                .message(message)
                .build(), status);
    }

    @ExceptionHandler(value = RecordAlreadyExistException.class)
    public ResponseEntity<ResponseDto> handleRecordAlreadyExist(RecordAlreadyExistException exception) {
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(ResponseDto.builder().status(status.name())
                .code(30002)
                .message(exception.getMessage())
                .build(), status);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<ResponseDto> handleRecordNotFound(RecordNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(ResponseDto.builder().status(status.name())
                .code(30000)
                .message(exception.getMessage())
                .build(), status);
    }


}
