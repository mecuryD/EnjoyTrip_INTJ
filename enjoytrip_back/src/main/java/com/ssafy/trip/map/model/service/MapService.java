package com.ssafy.trip.map.model.service;

import java.util.List;

import com.ssafy.trip.map.model.AttractionInfoDto;
import com.ssafy.trip.map.model.GugunDto;
import com.ssafy.trip.map.model.HeartDto;
import com.ssafy.trip.map.model.SearchDto;
import com.ssafy.trip.map.model.SidoDto;

public interface MapService {
	// 시도 데이터 반환
	List<SidoDto> getSido() throws Exception;
	
	// 구군 데이터 반환
	List<GugunDto> getGugun(int sidoCode) throws Exception;
	
	// 여행지 검색 조회
	List<AttractionInfoDto> searchMap(SearchDto searchDto)throws Exception;

	// 여행지 상세 조회
	AttractionInfoDto detailMap(int contentId) throws Exception;

	// 여행지 검색 데이터 저장
	void saveSearch(SearchDto searchDto) throws Exception;

	// 여행지 찜리스트 반환
	List<AttractionInfoDto> getMapHeart(String userId) throws Exception;
	
	HeartDto isHeart(HeartDto heartDto) throws Exception;

	// 여행지 찜 등록/해제
	void putHeart(HeartDto heartDto) throws Exception;
}