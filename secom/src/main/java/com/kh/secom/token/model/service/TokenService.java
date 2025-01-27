package com.kh.secom.token.model.service;

import java.util.Map;

public interface TokenService {
	
	Map<String, String> generateToken(String username, Long userNo);

	Map<String, String> refreshTokens(String refreshToken);

}
