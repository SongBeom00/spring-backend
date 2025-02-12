package org.songbeom.mallapi.controller.advice;


import org.songbeom.mallapi.util.CustomJWTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice // 예외 처리하기.
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class) //만약 존재하는 데이터가 없으면 404에러 json 반환
    protected ResponseEntity<?> notExist(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg",e.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //http://localhost:8080/api/todo/list?page=AAA
    protected ResponseEntity<?> notExist(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("msg",e.getMessage()));
    }

    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleCustomJWTException(CustomJWTException e) {
        String msg = e.getMessage();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", msg));
    }
}
