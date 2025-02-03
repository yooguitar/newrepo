package com.kh.secom.board.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class BoardDTO {
	
	private Long boardNo;
	@NotBlank(message = "게시글 제목은 비어있을 수 없어요.")
	private String boardTitle;
	@NotBlank(message = "게시글 내용은 비어있을 수 없어요.")
	private String boardContent;
	@NotBlank(message = "게시글 작성자는 비어있을 수 없어요.")
	private String boardWriter;
	private String boardFileUrl;
	// 사이즈도 필요하면 설정
}

/*
 * DTO에 기본생성자가 없다면 403 발생 할 수 있음
 * => IlligalException....
 */
