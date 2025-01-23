package com.kh.secom.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Member {
	private Long userNo;
	private String userId;
	private String userPwd;
	private String role;
}
/*
 * VO는 기본생성자, setter가 없는게 국룰임 
 */
