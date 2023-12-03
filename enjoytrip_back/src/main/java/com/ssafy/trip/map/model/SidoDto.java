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
@ApiModel(value = "SidoCodeDto : 시도정보", description = "시도의 이름을 나타낸다.")
public class SidoDto {
	@ApiModelProperty(value = "시도코드")
	private int sidoCode;
	@ApiModelProperty(value ="시도명")
	private String sidoName;
}
