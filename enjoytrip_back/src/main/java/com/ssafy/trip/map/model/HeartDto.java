package com.ssafy.trip.map.model;

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
@ApiModel(value = "HeartDto : 찜 테이블", description = "찜한 사람의 목록을 가져온다")
public class HeartDto {
	private String userId;
	private int contentId;
	private String heartCreateDate;
}
