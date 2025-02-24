package com.kh.secom.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.kh.secom.auth.service.UserServiceImpl;
import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.board.model.service.BoardService;

@WebMvcTest(BoardController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BoardControllerSliceTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private BoardService boardService;
	@MockBean
	private JwtUtil util;
	@MockBean
	private UserServiceImpl service;
	
	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/boards")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}
	// import http.MediaType;
	
}
