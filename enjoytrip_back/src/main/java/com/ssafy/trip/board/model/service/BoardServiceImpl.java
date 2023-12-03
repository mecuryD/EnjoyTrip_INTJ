package com.ssafy.trip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.model.mapper.BoardMapper;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	@Transactional
	public void registerBoard(BoardDto boardDto) throws Exception {
		boardMapper.registerBoard(boardDto);
	}
	
	@Override
	public List<BoardDto> listNotice() throws Exception {
		return boardMapper.listNotice();
	}

	@Override
	public List<BoardDto> listBoard() throws Exception {
		return boardMapper.listBoard();
	}

	@Override
	public void updateBoardView(int boardId) throws Exception {
		boardMapper.updateBoardView(boardId);
	}


	@Override
	public BoardDto getBoard(int boardId) throws Exception {
		return boardMapper.getBoard(boardId);
	}

	@Override
	public void modifyBoard(BoardDto boardDto) throws Exception {
		boardMapper.modifyBoard(boardDto);
	}

	@Override
	@Transactional
	public void deleteBoard(int boardId) throws Exception {
		boardMapper.deleteBoard(boardId);
	}

	// 댓글 관련 기능
	@Override
	public void registerComment(CommentDto commentDto) throws Exception {
		boardMapper.registerComment(commentDto);
	}

	@Override
	public List<CommentDto> listComment(int boardId) throws Exception {
		return boardMapper.listComment(boardId);
	}

	@Override
	@Transactional
	public void deleteComment(int commentId) throws Exception {
		boardMapper.deleteComment(commentId);
	}
}
