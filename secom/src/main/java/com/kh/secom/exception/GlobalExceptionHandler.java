package com.kh.secom.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MissmatchPasswordException.class)
	public ResponseEntity<?> handleMismatchPassword(MissmatchPasswordException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(JwtTokenException.class)
	public ResponseEntity<?> handleInvalidToken(JwtTokenException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(AccessTokenExpiredException.class)
	public ResponseEntity<?> handleExpiredToken(AccessTokenExpiredException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleInvalidParameter(InvalidParameterException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<String> handleDuplicateUser(DuplicateUserException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class) // 오타 찾기
	public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap();
		/*
		 * List list = e.getBindingResult().getFieldErrors(); // 에러가 <FieldError>타입으로 2개
		 * 반환된다 // 400 에러는 잘못된 요청이 온 것 이다. for(int i = 0; i < list.size(); i++) {
		 * log.info("에외가 발생한 필드명 : {}, 이유 : {}", ((FieldError)list.get(i)).getField(),
		 * ((FieldError)list.get(i)).getDefaultMessage()); // 잘 나온다면 json 형태로 앞단에 넘겨보자
		 * // hashMap에 담으므로 K,V 형태로 put 한다 errors.put(
		 * ((FieldError)list.get(i)).getField(),
		 * ((FieldError)list.get(i)).getDefaultMessage() ); }
		 */

		// 자바에서도 함수형 프로그래밍 문법을 사용 할 수 있다!!
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.badRequest().body(errors);
	}

}
