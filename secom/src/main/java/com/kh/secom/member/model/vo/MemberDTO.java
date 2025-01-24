package com.kh.secom.member.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class MemberDTO {
	// DTO에 Validation을 달아보자
	// 값을 검증할 핸들러 위에 @Valid를 달아야 한다
	// ※이건 시큐리티, 토큰과는 상관 없는 기능임!

	private Long userNo;

	@Pattern(regexp = "[a-zA-Z0-9]*$", message = "아이디 영어나 숫자만 쓰세용")
	@Size(min = 4, max = 15, message = "아이디 4-15자 사이로 입력하세요")
	@NotBlank(message = "아이디 값은 비어있을 수 없어요")
	private String userId;

	@Pattern(regexp = "[a-zA-Z0-9]*$", message = "비번 영어나 숫자만 쓰세용")
	@Size(min = 4, max = 20, message = "비번 4-20자 사이로 입력하세요")
	@NotBlank(message = "비번은 비어있을 수 없어요")
	private String userPwd;

	private String role;
}

// 검증 클래스는 자카르타 import
// validation starter
