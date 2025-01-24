package com.kh.secom.auth.util;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.secom.auth.service.UserServiceImpl;
import com.kh.secom.exception.AccessTokenExpiredException;
import com.kh.secom.exception.JwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
// Servlet Filter 대신 OncePerRequestFilter를 구현하면 코드 중복이 줄어드는 이점이 있다.

	private final JwtUtil tokenUtil;
	private final UserServiceImpl userService; // **스프링 시큐리티는 userDetails에 인증된 사용자의 정보를 담고 있다. 필드 등록하고 사용

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("우리의 토큰 필터 출동 {}", authorization);
		// Header에서 토큰을 보낼 때 맨 앞에 "Bearer "를 붙여준다. 컨벤션이다.
		// ""빼고 넣기

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("authorization이 존재하지 않아요~");
			filterChain.doFilter(request, response);
			return;
		}
		// 조건문을 통과한 요청일 경우 토큰만 쏙 뽑아서 변수에 담기
		String token = authorization.split(" ")[1];
		// 1. 이거 내 비밀키로 만든거임?
		// 2. 이거 유효기간 안지남? => 이 작업들은 tokenUtil.parseJwt()가 수행
		try {
			Claims claims = tokenUtil.parseJwt(token);

			String username = claims.getSubject();
			log.info("토큰 주인 아이디 : {}", username);

			// 사용자 정보 로드
			UserDetails userDetails = userService.loadUserByUsername(username);
			// 여기서 userDetails에는 principal값이 들어있다
			
			// 2번째 인자에 비밀번호가 들어가야 하지만 토큰 방식에서는 필요 없으므로 null 대입
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			// 세부설정: 사용자의 원격주소, MAC주소, 세션 ID등이 포함될 수 있음
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// SecurityContextHolder > SecurityContext > Authentication
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (ExpiredJwtException e) {
			log.info("AccessToken이 만료");
			//throw new AccessTokenExpiredException("토큰이 만료되었습니다");
			
			// 필터단에서 예외처리가 되었다면 응답은 돌아가지 않는다
			// response를 사용해 임시방편이 가능하다
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Expired Token");
			return;
		} catch (JwtException e) {
			log.info("Token 검증에 실패");
			//throw new JwtTokenException("이상요상~~");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("이상요상~");
			return;
		}
		// 조건검사를 모두 통과했다면 필터 체인으로 돌아간다
		filterChain.doFilter(request, response);
	}

}
