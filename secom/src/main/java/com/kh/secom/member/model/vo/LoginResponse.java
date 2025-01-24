package com.kh.secom.member.model.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
@Setter
@Getter
public class LoginResponse {
	private String username;
	private Map<String, String> tokens;
}
/*
 * org.springframework.http.converter.HttpMessageNotWritableException: No converter...
 * 원인 : 일반적으로 Spring이 반환 된 객체의 속성을 가져 오지 못할 때 발생.
 * 해결 : 오류가 발생한 DTO 클래스에 @Getter 추가하여 해결
 * 참고 :
 * 	Spring Boot는 Jackson 라이브러리에 의존하여 요청 및 응답 객체를 직렬화 / 역 직렬화하는 모든 작업을 수행함 
 * 	따라서 예외의 또 다른 일반적인 원인은 누락되거나 잘못된 Jackson 의존성을 사용하는 것 이다.
 * 
 * 	직렬화(Serialization)과 역직렬화(Deserialization)의 정의
 *	✅ 직렬화 : 객체들의 데이터를 연속적인 데이터(스트림)로 변형하여 전송 가능한 형태로 만드는 것
 *	✅ 역직렬화 : 직렬화된 데이터를 다시 객체의 형태로 만드는 것
 *  객체 데이터를 통신하기 쉬운 포멧(Byte,CSV,Json..) 형태로 만들어주는 작업을 직렬화라고 볼 수 있고,
 *	역으로, 포멧(Byte,CSV,Json..) 형태에서 객체로 변환하는 과정을 역직렬화라고 할 수 있겠다.
 */
