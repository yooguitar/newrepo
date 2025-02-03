package com.kh.secom.comment.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@ToString
public class Comment {

	private Long commentNo;
	private Long refBoardNo;
	private String commentWriter;
	@NotBlank(message = "댓글 내용은 비어있을 수 없습니다~")
	private String content;
	private LocalDateTime createDate;
	
}
