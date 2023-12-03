package com.ssafy.trip.board.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
@Api(tags= {"게시판 컨트롤러  API V1"})
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 게시판 관련 기능
	@ApiOperation(value = "글 목록", notes = "게시글 목록을 불러옵니다.")
	@GetMapping
	public ResponseEntity<Map<String, Object>> list () throws Exception {
		try {
			List<BoardDto> listNotice = boardService.listNotice();
			List<BoardDto> listBoard = boardService.listBoard();
			
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("notice", listNotice); // 공지사항
			resultMap.put("board", listBoard); // 일반 유저 게시글
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(resultMap);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ApiOperation(value = "글 등록", notes = "게시글을 등록합니다.")
	@PostMapping
	public ResponseEntity<Objects> registerBoard(@RequestBody BoardDto boardDto){
		try {
			boardService.registerBoard(boardDto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ApiOperation(value = "수정할 글 가져오기", notes = "DB에서 수정할 게시글 정보를 가져옵니다.")
	@GetMapping("/modify/{boardId}")
	public ResponseEntity<BoardDto> getModify(@PathVariable("boardId") int boardId) throws Exception{
		try {
			return ResponseEntity.ok().body(boardService.getBoard(boardId));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ApiOperation(value = "글 수정", notes = "게시글을 수정합니다.")
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody BoardDto boardDto){
		try {
			boardService.modifyBoard(boardDto);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ApiOperation(value = "글 상세보기", notes = "게시글 상세 보기를 불러옵니다.")
	@GetMapping("/{boardId}")
	public ResponseEntity<Map<String,Object>> getBoard(@PathVariable("boardId") int boardId){
		try {
			boardService.updateBoardView(boardId); // 조회수 증가
			BoardDto boardDto = boardService.getBoard(boardId); // 글 상세
			List<CommentDto> commentList = boardService.listComment(boardId); // 글에 등록된 댓글
			
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("boardInfo", boardDto);
			resultMap.put("commentList", commentList);
			return ResponseEntity.ok().body(resultMap);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ApiOperation(value = "글 삭제", notes = "게시글을 삭제합니다.")
	@DeleteMapping("/{boardId}")
	public ResponseEntity<Objects> deleteBoard(@PathVariable("boardId") int boardId) {
		log.info("deleteBoard - 호출");
		try {
			boardService.deleteBoard(boardId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// 댓글 관련 기능
	@ApiOperation(value = "댓글 등록", notes = "댓글을 등록합니다.")
	@PostMapping("/comment")
	public ResponseEntity<Objects> registerComment(@RequestBody CommentDto commentDto){
		log.info("registerComment - 호출");
		try{
			boardService.registerComment(commentDto);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<Objects> deleteComment(@PathVariable("commentId") int commentId) throws Exception {
		log.info("deleteComment - 호출");
		try {
			boardService.deleteComment(commentId);
			return ResponseEntity.ok().build();			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
//	// 오류 발생 시, 내부 서버 오류 리턴
	// 와일드카드 사용을 줄이기 위해 메서드 대신 코드 반복으로 대체
//	private ResponseEntity<Objects> exceptionHandling(Exception e) {
//		e.printStackTrace();
//		return ResponseEntity.internalServerError().build();
//	}
	
}
