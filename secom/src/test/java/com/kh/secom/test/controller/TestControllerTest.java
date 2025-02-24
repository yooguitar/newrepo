package com.kh.secom.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestControllerTest {
	/* 
	 * 수업은 jUnit 5버전
	 * jUnit으로 테스트 하기 위해 @Test를 붙여준다.
	 * jUnit은 애노테이션 기반 테스트 프레임워크
	 * 만약 Before, AfterAll에 static 키워드를 사용하지 않겠다면
	 * @TestInstance(TestInstance.Lifecycle.PER_CLASS)
	 * 애노테이션을 달아준다
	 * */

	private TestController tc = new TestController();
	
	@BeforeAll
	public static void init() {
		log.info("테스트 시작!");
		// 테스트 수행 전 실행. 정적 메소드로 만든 후 실행하자
	}
	
	@Test
	public void test1() {
		assertEquals("안뇽1", tc.testPrint("안뇽"), "반환하는 문자열이 일치하지 않습니다."); 
		// 기대한 값과 실제 결과가 같은지 비교하는 static 메소드
		// => 인자1: 예상결과, 인자2 request
		// 선택의 영역 => 인자3: ErrorMsg
	}
	
	@Test
	public void test2() {
		assertEquals(3, tc.testPlus(1, 2));
	}
	
	@Test
	public void test3() {
		assertThrows(IllegalArgumentException.class,() -> tc.testException(-1));
	}
	
	@AfterAll
	public static void close() {
		log.info("테스트 종료!");
	}
}

