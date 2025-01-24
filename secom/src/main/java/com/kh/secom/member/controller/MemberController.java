package com.kh.secom.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.member.model.service.MemberService;
import com.kh.secom.member.model.vo.ChangePasswordDTO;
import com.kh.secom.member.model.vo.LoginResponse;
import com.kh.secom.member.model.vo.MemberDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "members", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final AuthenticationService authService;

	// 새롭게 데이터를 만들어내는 요청(INSERT) == POST
	// 200ok와 함께 돌려보내면 best
	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody MemberDTO requestMember) {
		// body 영역의 데이터를 받는다면 애노테이션을 붙여 어떤값을 받는지 명확하게 한다
		// log.info("요청한 사용자의 데이터 : {}", requestMember); // 잘 찍힌다.
		memberService.save(requestMember);
		return ResponseEntity.ok("회원가입이 성공했습니다.");
	}

	/**
	 * Flow: getRequest -> Filter -> requestHandler -> { Validation ->
	 * GlobalExceptionHandler } -> Service(Authentication) -> {
	 * AuthenticationManager -> UserDetailsService -> return CustomUserDetails } ->
	 * Authentication(principal) -> { TokenService(Map Token, Map Token) ->
	 * TokenUtil } -> Service(Map Token) -> return ResponseEntity -> return
	 * jsonEntity;
	 */
	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody MemberDTO requestMember) {
		/*
		 * 로그인에 성공했을 때?? 인증과정 => 개발자가 했었음. id / pwd(평문) id / pwd(암호문)
		 */
		Map<String, String> tokens = authService.login(requestMember);
		// 로그인에 성공 했을 때
		// AccessToken
		// RefreshToken 반환

		LoginResponse response = LoginResponse.builder().username(requestMember.getUserId()).tokens(tokens).build();

		return ResponseEntity.ok(response);
	}

	// 비밀번호 변경 기능 구현
	// 기존 비밀번호 / 바꾸고 싶은 비밀번호
	@PutMapping
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changeEntity) {
		// log.info("{}", changeEntity);
		memberService.changePassword(changeEntity);
		return ResponseEntity.ok("업데이트 성공~");
	}

}
