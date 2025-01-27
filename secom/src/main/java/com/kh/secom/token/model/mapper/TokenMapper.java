package com.kh.secom.token.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.secom.token.model.dto.RefreshTokenDTO;

@Mapper
public interface TokenMapper {
	
	@Insert("INSERT INTO TB_REFRESH_TOKEN VALUES(#{userNo}, #{token}, #{expiration})")
	void saveToken(RefreshTokenDTO refreshToken);

	@Select("SELECT USER_NO userNo, TOKEN token, EXPIRED_AT expiration FROM TB_REFRESH_TOKEN WHERE TOKEN=#{refreshToken}")
	RefreshTokenDTO findByToken(String refreshToken);

	void deleteExpiredRefreshToken(Map<String, Long> params);
	
}
