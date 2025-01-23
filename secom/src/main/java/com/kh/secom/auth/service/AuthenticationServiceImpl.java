package com.kh.secom.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwt;
	
	@Override
	public Map<String, String> login(MemberDTO requestMember){
		// 사용자 인증
		Authentication authentication =  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestMember.getUserId(),
													    requestMember.getUserPwd()));
		/*
		 * 사용자가 입력한 username과 password를 검증하는 용도로 사용하는 클래스
		 * 주로, SpringSecurity에서 인증을 시도할 때 사용함
		 */
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		log.info("로그인절차 성공!");
		log.info("DB에서 조회된 사용자의 정보 : {}", user);

		// 여기까지 왔다면 시큐리티 1절은 끝났다
		// gradle에 쓸 수 있는 JWT토큰 만들기 라이브러리를 사용하자
		
		String accessToken = jwt.getAccessToken(user.getUsername());
		log.info("액세스 토큰 발급! : {}", accessToken);
		return null;
	}

}
