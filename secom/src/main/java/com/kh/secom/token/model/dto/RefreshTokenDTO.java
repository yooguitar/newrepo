package com.kh.secom.token.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
@Setter
public class RefreshTokenDTO {
	private String token;
	private Long userNo;
	private Long expiration;
}
