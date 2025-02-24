package com.kh.secom.board.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

/**
 * BoardController의 단위 테스트를 담당할 클래스
 * 	**단위 테스트(Unit test)란?
 *   단위테스트에서는 내부 로직만 테스트 하기 때문에,
 *   실제 외부 의존성을 사용하지 않고 가짜(mock) 객체를 주입해서 테스트
 */
@Slf4j
public class BoardControllerUnitTest {
	
	/**
	 * 테스트 시, 실제 BoardService 대신에 가짜 객체를 만들어서 사용함!
	 * 가짜 데이터를 반환하도록 미리 동작을 정의해서 사용할 예정
	 */
	@Mock
	private BoardService service;
	
	/**
	 * Mock 객체를 BoardController에 주입하는 애노테이션
	 */
	@InjectMocks
	private BoardController boardController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		// 초기화 수행 시 controller에 mock service를 주입한다
	}
	
	@Test
	public void testFindById() {
		/*
		 * 테스트를 위한 가짜 객체를 생성(Board)
		 * 가짜 객체의 boardTitle필드에 임의의 값 대입
		 * 
		 * 참고로 여기서 임의의 값은 나중에 검증하는 기대값 대입
		 */
		BoardDTO board = new BoardDTO();
		board.setBoardTitle("11번 게시글 제목");
		
		//  *** given / when / then
		
		when(service.findById(11L)).thenReturn(board);
		// BoardController 내부에서 service.findById(11L)을 호출했을 때,
		// 실제 DB에서 조회한 값이 아닌 테스트를 위해 만든 가짜 board를 반환하도록 세팅
		
		var response = boardController.findById(11L);
		// var를 사용할 수는 있다. 하지만 권장사항이 아니다
		// => 명확한 자료형을 다루는 자바의 장점을 잃게 됨
		
		// log.info("이거 어떻게옴? {}", response);
		// 상태코드, body(가짜데이터), [header] 반환
		
		BoardDTO boardResponse = response.getBody();
		
		assertEquals("11번 게시글 제목", boardResponse.getBoardTitle());
		
	}
	
	/*
	 * TDD(TestDrivenDevelopment): 테스트 주도 개발
	 * 
	 * 1. 실패하는 코드를 작성한다. (실패하는 케이스를 만들고 예외처리)   // RED
	 * 2. 테스트를 통과할 수 있도록 최소한의 코드만 작성				 // Green
	 * 3. 코드 정리하고 개선하기								// Refactoring
	 * 
	 * 이렇게 개발하면 장기적으로 유지보수에 유리하다
	 * 다른사람이 코드를 보게 됐을때에도 코드의 설명와 같은 역할을 한다
	 * 
	 * 단점 : 개발 시간이 늘어남
	 */
	

}
