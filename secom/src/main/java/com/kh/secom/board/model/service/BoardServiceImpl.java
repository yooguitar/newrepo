package com.kh.secom.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.mapper.BoardMapper;
import com.kh.secom.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final FileService fileService;
	private final BoardMapper boardMapper;
	private final AuthenticationService authService;

	@Override
	public void save(BoardDTO board, MultipartFile file) {
		// log.info("게시글 정보 : {} \n파일정보 : {}", board, file);
		// 요청 테스트 전에 SecurityConfig에서 requestMatchers 추가하자
		// 토큰 인증할 때 앞에 Bearer 꼭 붙이자
		// 파일 업로드도 해야한다. 포스트맨 form-data 형식 테스트

		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(board.getBoardWriter(), user.getUsername());

		if (file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			board.setBoardFileUrl(filePath);
		} else {
			board.setBoardFileUrl(null);
		}

		board.setBoardWriter(String.valueOf(user.getUserNo()));

		boardMapper.save(board);

	}

	@Override
	public List<BoardDTO> findAll(int page) {
		int size = 3;
		RowBounds rowBounds = new RowBounds(page * size, size);
		return boardMapper.findAll(rowBounds);
	}

	private BoardDTO getBoardOrThrow(Long boardNo) {
		BoardDTO board = boardMapper.findById(boardNo);
		if (board == null) {
			throw new InvalidParameterException("올바르지 않은 게시글 번호입니다.");
		}
		return board;
	}

	@Override
	public BoardDTO findById(Long boardNo) {
		return getBoardOrThrow(boardNo);
	}

	@Override
	public BoardDTO update(BoardDTO board, MultipartFile file) {
		BoardDTO exsitingBoard = getBoardOrThrow(board.getBoardNo());
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(board.getBoardWriter(), user.getUsername());
		
		exsitingBoard.setBoardTitle(board.getBoardTitle());
		exsitingBoard.setBoardContent(board.getBoardContent());
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			exsitingBoard.setBoardFileUrl(filePath);
		}
		
		boardMapper.update(exsitingBoard);
		
		return exsitingBoard;
	}

	@Override
	public void deleteById(Long boardNo) {
		BoardDTO exsitingBoard = getBoardOrThrow(boardNo);
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(exsitingBoard.getBoardWriter(), user.getUsername());
		
		boardMapper.deleteById(boardNo);
	}

}
