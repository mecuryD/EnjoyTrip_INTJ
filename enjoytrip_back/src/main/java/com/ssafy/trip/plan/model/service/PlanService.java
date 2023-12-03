package com.ssafy.trip.plan.model.service;

import java.util.List;

import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanInfoDto;

public interface PlanService {
	
	// 여행 계획 등록
	public void registPlan(PlanDto planDto, List<PlanInfoDto> planInfoDto) throws Exception;
	
	// 여행 계획 목록
	public List<PlanDto> listPlan() throws Exception;
	public List<PlanDto> listMyPlan(String userId) throws Exception;
	
	// 여행 계획 삭제
	public void deletePlan(int planId) throws Exception;
	
	// 여행 계획 상세 조회
	public PlanDto detailPlan(int planId) throws Exception;
	
	// 여행 계획 수정
	public void modifyPlan(PlanDto planDto) throws Exception;
}
