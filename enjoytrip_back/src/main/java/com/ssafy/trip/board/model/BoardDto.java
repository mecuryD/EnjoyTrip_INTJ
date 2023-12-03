package com.ssafy.trip.board.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ApiModel(value="BoardDto : 게시글 정보", description = "글 타입, 작성자, 제목, 내용 등에 대한 정보를 담은 Domain Class")
public class BoardDto {
	
	private int boardType;
	@NonNull
	private int boardId;
	private String userId;
	private String boardSubject;
	private String boardContent;
	private int boardView;
	private String boardCreateDate;
	// 댓글 정보
	private int commentCount;
}
