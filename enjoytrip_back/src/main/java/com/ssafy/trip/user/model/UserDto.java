package com.ssafy.trip.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value="UserDto (회원 정보)", description = "회원 등급, 아이디, 비밀번호, 이름, 권한 등 회원 정보에 대한 정보를 가진 Domain Class")
public class UserDto {

	@ApiModelProperty(value = "회원 등급")
	private int level;
	
	@ApiModelProperty(value = "회원 아이디")
	private String userId;
	
	@ApiModelProperty(value = "회원 비밀번호")
	private String userPwd;
	
	@ApiModelProperty(value = "회원  이름")
	private String userName;
	
	@ApiModelProperty(value = "생년월일")
	private String birth;
	
	@ApiModelProperty(value = "성별")
	private int gender;
	
	@ApiModelProperty(value = "이메일 아이디")
	private String emailId;
	
	@ApiModelProperty(value = "이메일 도메인")
	private String emailDomain;
	
	@ApiModelProperty(value = "가입일")
	private String joinDate;
	
	@ApiModelProperty(value = "리프레시 토큰")
	private String refreshToken;
}
