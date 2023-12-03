package com.ssafy.trip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanInfoDto;

@Mapper
public interface PlanMapper {
	
	// 여행 계획 등록
	void registPlan(PlanDto planDto) throws SQLException;
	void registPlanInfo(List<PlanInfoDto> planInfoDto) throws SQLException;
	
	// 여행 계획 목록
	List<PlanDto> listPlan() throws SQLException;
	List<PlanDto> listMyPlan(String userId) throws SQLException;

	// 여행 계획 삭제
	void deletePlan(int planId) throws SQLException;
	
	// 여행 계획 상세
	PlanDto detailPlan(int planId) throws SQLException;
	
	// 여행 계획 수정
	void modifyPlan(PlanDto planDto) throws SQLException;
}
