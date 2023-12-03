package com.ssafy.trip.plan.model;

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
@ApiModel(value="PlanInfoDto : 여행 계획 정보", description = "여행 경로에 따른 관광지 순서 정보를 담은 Domain Class")
public class PlanInfoDto {
	private int planId;
	private String planOrder;
	private int contentId;
	private String contentMemo;
}
