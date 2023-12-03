package com.ssafy.trip.map.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.map.model.AttractionInfoDto;
import com.ssafy.trip.map.model.GugunDto;
import com.ssafy.trip.map.model.HeartDto;
import com.ssafy.trip.map.model.SearchDto;
import com.ssafy.trip.map.model.SidoDto;
import com.ssafy.trip.map.model.mapper.MapMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MapServiceImpl implements MapService {
	
	@Autowired
	private MapMapper mapMapper;

	// 시도 코드 조회
	@Override
	public List<SidoDto> getSido() throws Exception {
		return mapMapper.getSido();
	}

	// 구군 코드 조회
	@Override
	public List<GugunDto> getGugun(int sidoCode) throws Exception {
		return mapMapper.getGugun(sidoCode);
	}
	
	// 여행지 검색 조회
	@Override
	public List<AttractionInfoDto> searchMap(SearchDto searchDto) throws Exception {
		return mapMapper.searchMap(searchDto);
	}

	// 여행지 상세 조회
	@Override
	public AttractionInfoDto detailMap(int contentId) throws Exception {
		return mapMapper.detailMap(contentId);
	}

	// 검색 데이터 저장
	@Override
	public void saveSearch(SearchDto searchDto) throws Exception {
		mapMapper.saveSearch(searchDto);
	}

	// 여행지 찜리스트 조회
	@Override
	public List<AttractionInfoDto> getMapHeart(String userId) throws Exception {
		return mapMapper.getMapHeart(userId);
	}

	// 여행지 찜 등록 / 헤제
	@Override
	public void putHeart(HeartDto heartDto) throws Exception {
		// 찜 여부 확인
		HeartDto resultDto = mapMapper.existHeart(heartDto);
		log.info("resultDto : {}", resultDto);
		// 찜이 되어 있지 않다면 새로 등록하고, 이미 있다면 해제
		if(resultDto == null) {
			log.info("새로 등록");
			mapMapper.addHeart(heartDto);
		}else {
			log.info("제거");
			mapMapper.deleteHeart(heartDto);
		}
	}

	@Override
	public HeartDto isHeart(HeartDto heartDto) throws Exception {
		// 찜 여부 확인
		return mapMapper.existHeart(heartDto);
	}
}
