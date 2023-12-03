package com.ssafy.trip.map.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class SearchDto {
	@NonNull
	private int searchId;
	@NonNull
	private String userId;
	private String birth;
	private int gender;
	private int sidoCode;
	private int gugunCode;
	private int contentTypeId;
	private String searchContent;
}
