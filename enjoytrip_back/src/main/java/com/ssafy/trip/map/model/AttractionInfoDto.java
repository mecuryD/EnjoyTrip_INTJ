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
@ApiModel(value="AttractionInfoDto : 여행지 정보", description = "여행지명, 주소, 위도, 경도 등 여행지에 대한 정보를 가진 Domain Class")
public class AttractionInfoDto {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String firstImage;
	private int sidoCode;
	private int gugunCode;
	private String latitude;
	private String longitude;
	private String overview;
}
