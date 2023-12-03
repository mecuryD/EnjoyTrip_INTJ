package com.ssafy.trip.map.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.map.model.AttractionInfoDto;
import com.ssafy.trip.map.model.GugunDto;
import com.ssafy.trip.map.model.HeartDto;
import com.ssafy.trip.map.model.SearchDto;
import com.ssafy.trip.map.model.SidoDto;

@Mapper
public interface MapMapper {

	// 시도 코드 조회
	List<SidoDto> getSido() throws SQLException;

	// 구군 코드 조회
	List<GugunDto> getGugun(int sidoCode) throws SQLException;

	// 여행지 검색 조회
	List<AttractionInfoDto> searchMap(SearchDto searchDto) throws SQLException;

	// 여행지 상세 조회
	AttractionInfoDto detailMap(int contentId) throws SQLException;
	
	// 여행지 검색 데이터 저장
	void saveSearch(SearchDto searchDto) throws Exception;
	
	// 여행지 찜리스트 반환
	List<AttractionInfoDto> getMapHeart(String userId) throws SQLException;
	
	// 여행지 찜 여부 확인
	HeartDto existHeart(HeartDto heartDto) throws SQLException;
	
	// 여행지 찜하기
	void addHeart(HeartDto heartDto) throws SQLException;
	
	// 여행지 찜하기 해제
	void deleteHeart(HeartDto heartDto) throws SQLException;
	
}
