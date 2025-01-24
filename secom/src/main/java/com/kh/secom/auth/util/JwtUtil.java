package com.kh.secom.auth.util;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	// 토큰 12버전 토큰만들기 토큰만들기 토큰만들기
	// 시크릿키는 검색해서 생성기 쓰거나 powershell로 만든다
	// + 보안의 이유로 시크릿키는 코드에 적지 않는다. 외부에 파일로 빼서 보관하는게 정석

	// 애플리케이션 설정 파일(application.properties /application.yml)에 정의된
	// 속성의 값들을 @Value애노테이션을 이용해서 값을 주입 받을 수 있음
	@Value("${jwt.secret}")
	private String secretKey;
	// javax.crypto.SecretKey타입의 필드로 JWT서명에 사용할 수 있음
	private SecretKey key;

	private long ACCESS_TOKEN_EXPIRED = 3600000L * 24; // 1일이다 ms단위
	private long REFRESH_TOKEN_EXPIRED = 3600000L * 72; // 3일이다

	@PostConstruct // Bean 초기화 시 필요한 추가 설정들을 할 수 있음
	public void init() {
		// 시크릿키를 byte형 배열로 디코딩
		byte[] keyArr = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyArr);

		// System.currentTimeMillis() => 현재 시간을 long 타입으로 바꿔준다
	}

	private Date buildExpirationDate(long date) {
		// 중복코드 줄이기
		long now = System.currentTimeMillis();
		return new Date(now + date);
	}

	public String getAccessToken(String username) {
		return Jwts.builder().subject(username) // 사용자 이름
				.issuedAt(new Date()) // 발급일(토큰생성일)
				.expiration(buildExpirationDate(ACCESS_TOKEN_EXPIRED)) // 만료일
				//.expiration(new Date()) 만료된 토큰 테스트 io.jsonwebtoken.ExpiredJwtException:
				.signWith(key) // 비밀키로 만든 서명
				.compact();
	}

	public String getRefreshToken(String username) {
		return Jwts.builder().subject(username).issuedAt(new Date())
				.expiration(buildExpirationDate(REFRESH_TOKEN_EXPIRED)).signWith(key).compact();
	}

	public Claims parseJwt(String token){
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	}
	
}
