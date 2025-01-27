package com.kh.secom.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.token.model.dto.RefreshTokenDTO;
import com.kh.secom.token.model.mapper.TokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JwtUtil tokenUtil;
	private final TokenMapper tokenMapper;

	@Override
	public Map<String, String> generateToken(String username, Long userNo) {
		// 1. 액세스 토큰 만들기
		// 2. 리프레시 토큰 만들기
		Map<String, String> tokens = createTokens(username);
		// 3. 리프리시 토큰 DB에 저장하기
		saveToken(tokens.get("refreshToken"), userNo);
		// 4. 만료기간이 지난 리프레시 토큰 있다면 지우기
		deleteExpiredRefreshToken(userNo);
		// 5. 사용자가 리프레시 토큰 증명하려 할 때 DB가서 조회해오기
		return tokens;
	}

	private void deleteExpiredRefreshToken(Long userNo) {
		Map<String, Long> params = new HashMap();
		params.put("userNo", userNo);
		params.put("currentTime", System.currentTimeMillis());
		log.info("currentTime : {}", System.currentTimeMillis());
		tokenMapper.deleteExpiredRefreshToken(params);
	}

	// 1번, 2번
	private Map<String, String> createTokens(String username) {
		String accessToken = tokenUtil.getAccessToken(username);
		String refreshToken = tokenUtil.getRefreshToken(username);

		Map<String, String> tokens = new HashMap();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);

		return tokens;
	}

	// 3번
	private void saveToken(String refreshToken, Long userNo) {
		RefreshTokenDTO token = RefreshTokenDTO.builder().token(refreshToken).userNo(userNo)
				.expiration(System.currentTimeMillis() + 3600000L * 72).build();
		tokenMapper.saveToken(token);
	}

	// 5번
	@Override
	public Map<String, String> refreshTokens(String refreshToken) {
		RefreshTokenDTO token = tokenMapper.findByToken(refreshToken);
		if(token == null || token.getExpiration() < System.currentTimeMillis()) {
			throw new RuntimeException("알 수 없는 리프레시 토큰이야~");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		return generateToken(user.getUsername(), user.getUserNo());
	}

}
