package com.ssafy.trip.plan.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanInfoDto;
import com.ssafy.trip.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanMapper planMapper;
	
	// 여행 계획 등록
	@Override
	public void registPlan(PlanDto planDto, List<PlanInfoDto> planInfoDto) throws SQLException {
		planMapper.registPlan(planDto);
		planMapper.registPlanInfo(planInfoDto);
	}

	// 여행 계획 목록
	@Override
	public List<PlanDto> listPlan() throws Exception {
		return planMapper.listPlan();
	}
	
	@Override
	public List<PlanDto> listMyPlan(String userId) throws Exception {
		return planMapper.listMyPlan(userId);
	}

	// 여행 계획 삭제
	@Override
	public void deletePlan(int planId) throws Exception {
		planMapper.deletePlan(planId);
	}

	// 여행 계획 상세 보기
	@Override
	public PlanDto detailPlan(int planId) throws Exception {
		return planMapper.detailPlan(planId);
	}

	// 여행 계획 수정
	@Override
	public void modifyPlan(PlanDto planDto) throws Exception {
		
		// 해당 경로가 여전히 존재하면 UPDATE
		// 해당 경로가 삭제되었으면 DELETE
		planMapper.modifyPlan(planDto);
	}
}
