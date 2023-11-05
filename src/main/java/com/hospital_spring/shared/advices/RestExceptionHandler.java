package com.hospital_spring.shared.advices;

import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.shared.exceptions.UserIsPresentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice // здесь перехватчики ошибок
public class RestExceptionHandler {
    // Обрабатывает ошибки "NotFoundException" (не найдено)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFound(NotFoundException exception) {
        log.error(exception.toString(), exception); // выводит ошибки в лог

        return new ResponseEntity<>(ResponseDto.fromException(exception, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    // Обрабатывает ошибки "UserIsPresentExceptions" (пользователь существует)
    @ExceptionHandler(UserIsPresentException.class)
    public ResponseEntity<ResponseDto> handleUserIsPresent(UserIsPresentException exception) {
        log.error(exception.toString(), exception);

        return new ResponseEntity<>(ResponseDto.fromException(exception, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
