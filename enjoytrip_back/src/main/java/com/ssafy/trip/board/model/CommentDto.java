package com.ssafy.trip.board.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CommentDto : 댓글 정보", description = "댓글 아이디, 작성자, 댓글 내용 등 댓글에 대한 정보를 가진 Domain Class")
public class CommentDto {
	private int boardId;
	private String userId;
	private int commentId;
	private String commentContent;
	private String commentCreatedDate;
}
