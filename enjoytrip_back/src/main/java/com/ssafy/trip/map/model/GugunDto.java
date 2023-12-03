package com.ssafy.trip.map.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "GugunCodeDto : 구군정보", description = "구군의 이름을 나타낸다.")
public class GugunDto {
	@ApiModelProperty(value = "구군코드")
	private int gugunCode;
	@ApiModelProperty(value = "구군명")
	private String gugunName;
	@ApiModelProperty(value = "시도코드")
	private int sidoCode;
	
}
