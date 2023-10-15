package com.hospital_spring.shared.advices;

import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.shared.dto.ExceptionDto;
import com.hospital_spring.shared.exceptions.UserIsPresentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice // здесь перехватчики ошибок
public class RestExceptionHandler {
    // Обрабатывает ошибки "NotFoundException" (не найдено)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFound(NotFoundException exception) {
        log.error(exception.toString()); // выводит ошибки в лог

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ExceptionDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build()
            );
    }

    // Обрабатывает ошибки "UserIsPresent" (пользователь существует)
    @ExceptionHandler(UserIsPresentException.class)
    public ResponseEntity<ExceptionDto> handleUserIsPresent(UserIsPresentException exception) {
        log.error(exception.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ExceptionDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
