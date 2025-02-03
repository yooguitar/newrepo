package com.kh.secom.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.board.model.service.BoardService;
import com.kh.secom.comment.model.dto.Comment;
import com.kh.secom.comment.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper commentMapper;
	private final BoardService boardService;
	private final AuthenticationService authService;

	@Override
	public void insertComment(Comment comment) {
		// 1. 게시글이 있는 게시글인지?
		boardService.findById(comment.getRefBoardNo());
		// 2. 사용자의 요청과 토큰에서 뽑힌 subject가 일치 하는가?
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(comment.getCommentWriter(), user.getUsername());
		comment.setCommentWriter(String.valueOf(user.getUserNo()));
		// 3. 위의 둘이 맞다면 insert
		commentMapper.insertComment(comment);
	}
	
	@Override
	public List<Comment> findByBoardNo(Long boardNo) {
		return commentMapper.findByBoardNo(boardNo);
	}

}
