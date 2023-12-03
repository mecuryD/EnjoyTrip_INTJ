package com.ssafy.trip.plan.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="PlanDto : 여행 계획 정보", description = "여행 경로, 작성자, 시작일, 종료일 등 여행 계획 정보를 담은 Domain Class")
public class PlanDto {
	
	private int planId;
	private String userId;
	private String planName;
	private String startDate;
	private String endDate;
	private String planMemo;
	private List<PlanInfoDto> planInfos;
}
