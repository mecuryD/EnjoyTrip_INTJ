package com.ssafy.trip.map.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.map.model.AttractionInfoDto;
import com.ssafy.trip.map.model.GugunDto;
import com.ssafy.trip.map.model.HeartDto;
import com.ssafy.trip.map.model.SearchDto;
import com.ssafy.trip.map.model.SidoDto;
import com.ssafy.trip.map.model.service.MapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/map")
@CrossOrigin("*")
@Api(tags= {"여행지 검색 컨트롤러  API V1"})
@Slf4j
public class MapController {
	
	@Autowired
	private MapService mapService;
	
	// 시도 정보 반환
	@ApiOperation(value = "시도 코드 조회", notes ="전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoDto>> getSido() throws Exception{
		log.info("getSido - 호출");
		return ResponseEntity.ok().body(mapService.getSido());
	}
	
	// 구군 정보 반환
	@ApiOperation(value = "구군 코드 조회", notes ="시도에 맞는 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<List<GugunDto>> getGugun(@PathVariable("sidoCode") int sidoCode) throws Exception {
		log.info("getGugun - 호출");
		return ResponseEntity.ok().body(mapService.getGugun(sidoCode));
	}

	// 여행지 검색 조회
	@ApiOperation(value = "여행지 검색 조회", notes = "검색 조건에 맞는 여행지 리스트를 반환합니다")
	@PostMapping
	public ResponseEntity<List<AttractionInfoDto>> searchMap(@RequestBody SearchDto searchDto) throws Exception{
//			@RequestParam(value="userId", required=false) String userId,
//			@RequestParam(value="sidoCode", required=false) int sidoCode,
//			@RequestParam(value="gugunCode", required=false) int gugunCode,
//			@RequestParam(value="contentTypeId", required=false) int contentTypeId,
//			@RequestParam(value="searchContent", required=false) String searchContent) throws Exception {
//		log.info("searchMap 호출");
//		log.info("userId: {}",userId);
//		log.info("sidoCode: {}",sidoCode);
//		log.info("gugunCode: {}",gugunCode);
//		log.info("contentTypeId: {}",contentTypeId);
//		log.info("searchContent: {}",searchContent);
		// Client에 전달할 리스트
		List<AttractionInfoDto> resultList = new ArrayList<AttractionInfoDto>();
		// 검색 데이터 저장
//		SearchDto searchDto = new SearchDto();
//		searchDto.setUserId(userId);
//		searchDto.setSidoCode(sidoCode);
//		searchDto.setGugunCode(gugunCode);
//		searchDto.setContentTypeId(contentTypeId);
//		searchDto.setSearchContent(searchContent);
		mapService.saveSearch(searchDto);
		// 검색 조건에 맞는 여행지 리스트 구성
		resultList = mapService.searchMap(searchDto);
		return ResponseEntity.ok().body(resultList);
	}
	
	// 여행지 상세 조회
	@ApiOperation(value = "여행지 상세 조회", notes = "여행지에 해당하는 상세 설명을 반환합니다")
	@GetMapping("/{contentId}")
	public ResponseEntity<AttractionInfoDto> detailMap(@PathVariable("contentId") int contentId) throws Exception{
		log.info("detailMap - 호출");
		return ResponseEntity.ok().body(mapService.detailMap(contentId));
	}
	
	// 사용자가 찜한 여행지 리스트 조회
	@ApiOperation(value = "여행지 찜리스트 조회", notes = "회원이 스크랩 한 여행지 리스트를 반환합니다 ")
	@GetMapping("/heart/{userId}")
	public ResponseEntity<List<AttractionInfoDto>> getHeart(@PathVariable("userId") String userId) throws Exception{
		log.info("getHeart- 호출");
		log.info("userId : {}", userId);
		return ResponseEntity.ok().body(mapService.getMapHeart(userId));
	}

	// 찜 목록 추가 / 제거
	@ApiOperation(value = "여행지 찜리스트 추가/삭제", notes = "회원이 선택한 여행지 찜하기 추가/삭제")
	@PostMapping("/heart")
	public ResponseEntity<Objects> putHeart(@RequestBody HeartDto heartDto){
		log.info("putHeart - 호출");
		try {
			mapService.putHeart(heartDto);
			return ResponseEntity.ok().build();			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	//사용자가 여행지 찜했는지 여부 조회
	@ApiOperation(value = "사용자 찜여부 조회", notes = "회원이 스크랩한 여행지라면 dto를 반환합니다")
	@PostMapping("/isheart")
	public ResponseEntity<HeartDto> isHeart(@RequestBody HeartDto heartDto){
		log.info("isHeart- 호출");
		try {
			return ResponseEntity.ok().body(mapService.isHeart(heartDto));			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
