package com.ssafy.trip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;

@Mapper
public interface BoardMapper {
	
	void registerBoard(BoardDto boardDto) throws SQLException;
	List<BoardDto> listNotice() throws SQLException;
	List<BoardDto> listBoard() throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	void updateBoardView(int boardId) throws SQLException;

	void modifyBoard(BoardDto boardDto) throws SQLException;
	BoardDto getBoard(int boardId) throws SQLException;
	void deleteBoard(int boardId) throws SQLException;
	
	// 댓글 관련 기능
	void registerComment(CommentDto commentDto) throws SQLException;
	List<CommentDto> listComment(int boardId) throws SQLException;
	void deleteComment(int commentId) throws SQLException;
	
}
