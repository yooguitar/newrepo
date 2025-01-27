package com.kh.secom.token.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class RefreshTokenDTO {
	private String token;
	private Long userNo;
	private Long expiration;
}
