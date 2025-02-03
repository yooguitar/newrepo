package com.kh.secom.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.service.BoardService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("boards")
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
public class BoardController {

	private final BoardService service;

	@PostMapping
	public ResponseEntity<?> save(@ModelAttribute @Valid BoardDTO board,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		service.save(board, file);
		return ResponseEntity.status(HttpStatus.CREATED).body("게시글 등록 성공!");
		// 추가 성공 시 201 return
	}

	@GetMapping
	public ResponseEntity<List<BoardDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page) {
		return ResponseEntity.ok(service.findAll(page));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardDTO> findById(
			@PathVariable(name = "id") @Min(value = 1, message = "0보다 작은 수 입니다.") Long boardNo) {
		// log.info("보드남바 : {}", boardNo);
		// 게시글 번호에 0 이하는 필요 없다. 매개변수에 @Min을 통해 유효성 검사 수행 @Validated 추가
		return ResponseEntity.ok(service.findById(boardNo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") Long boardNo, 
									@ModelAttribute @Valid BoardDTO board,
									@RequestParam(name = "file", required = false) MultipartFile file) {
		board.setBoardNo(boardNo);
		BoardDTO updated = service.update(board, file);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name="id") Long boardNo){
		service.deleteById(boardNo);
		return ResponseEntity.ok("잘 지웠어요~");
	}

}
