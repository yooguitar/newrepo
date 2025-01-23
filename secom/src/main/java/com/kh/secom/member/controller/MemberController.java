package com.kh.secom.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.member.model.service.MemberService;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final AuthenticationService authService;
	
	// 새롭게 데이터를 만들어내는 요청(INSERT) == POST
	// 200ok와 함께 돌려보내면 best
	@PostMapping
	public ResponseEntity<String> save(@RequestBody MemberDTO requestMember){ 
								  // body 영역의 데이터를 받는다면 애노테이션을 붙여 어떤값을 받는지 명확하게 한다
		//log.info("요청한 사용자의 데이터 : {}", requestMember); // 잘 찍힌다.
		memberService.save(requestMember);
		return ResponseEntity.ok("회원가입이 성공했습니다.");
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody MemberDTO requestMember){
		/*
		 * 로그인에 성공했을 때?? 인증과정 => 개발자가 했었음.  
		 * id / pwd(평문)
		 * id / pwd(암호문)
		 */
		authService.login(requestMember);
		
		// 로그인에 성공 했을 때
		// AccessToken
		// RefreshToken 반환
		return null;
	}
	/*
	 * 1. UserDetailService 구현하기
	 * 메소드를 하나 오버라이딩 할 예정
	 * 사용자가 입력한 username을 가지고 DB에 가서 조회
	 * 존재하지 않으면 예외 발생
	 * 존재한다면 조회된 정보를 가지고 UserDetails객체를 생성해서 반환
	 */
	
	
	
	
	
	
	
	
	
	
}
