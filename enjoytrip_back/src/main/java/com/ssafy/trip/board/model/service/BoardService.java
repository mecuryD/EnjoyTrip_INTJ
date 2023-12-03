package com.ssafy.trip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.util.PageNavigation;

public interface BoardService {
	
	// 게시글 관련 기능
	void registerBoard(BoardDto boardDto) throws Exception;
	List<BoardDto> listNotice() throws Exception;
	List<BoardDto> listBoard() throws Exception;
	void updateBoardView(int boardId) throws Exception;

	void modifyBoard(BoardDto boardDto) throws Exception;
	BoardDto getBoard(int boardId) throws Exception;
	void deleteBoard(int boardId) throws Exception;
	
	// 댓글 관련 기능
	void registerComment(CommentDto commentDto) throws Exception;
	List<CommentDto> listComment(int boardId) throws Exception;
	void deleteComment(int commentId) throws Exception;
}
