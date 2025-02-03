package com.kh.secom.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.member.model.vo.MemberDTO;
import com.kh.secom.token.model.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	// private final JwtUtil jwt;

	@Override
	public Map<String, String> login(MemberDTO requestMember) {
		// 사용자 인증
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestMember.getUserId(), requestMember.getUserPwd()));
		/*
		 * 사용자가 입력한 username과 password를 검증하는 용도로 사용하는 클래스 주로 SpringSecurity에서 인증을 시도할 때
		 * 사용함
		 */
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
		log.info("로그인 절차 성공!");
		log.info("DB에서 조회된 사용자의 정보 : {}", user);

		// 여기까지 왔다면 시큐리티 1절은 끝났다
		// gradle에 쓸 수 있는 JWT토큰 만들기 라이브러리를 사용하자
		/*
		 * String accessToken = jwt.getAccessToken(user.getUsername()); String
		 * refreshToken = jwt.getRefreshToken(user.getUsername());
		 * log.info("액세스 토큰 발급! : {}", accessToken);
		 */

		Map<String, String> tokens = tokenService.generateToken(user.getUsername(), user.getUserNo());
		
		return tokens;
	}
	
	public CustomUserDetails getAuthenticatedUser() {
		// Authentication을 얻어온다
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		return user;
	}

	public void validWriter(String writer, String username) {
		if (writer != null && !writer.equals(username)) {
			throw new RuntimeException("요청한 사용자와 게시글 작성자가 일치하지 않습니다.");
		}
	}

}
