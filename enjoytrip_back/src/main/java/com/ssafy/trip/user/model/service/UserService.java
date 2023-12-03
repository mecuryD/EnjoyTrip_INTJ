package com.ssafy.trip.user.model.service;

import java.util.List;

import com.ssafy.trip.user.model.UserDto;

public interface UserService {

	// 회원 가입
	public void registUser(UserDto userDto) throws Exception;
	
	// 회원 정보 조회
	List<UserDto> listUser() throws Exception;
	
	// 회원 정보 상세 조회
	UserDto getUser(String userId) throws Exception;
	
	// 회원 정보 수정
	void modifyUser(UserDto userDto) throws Exception;
	
	// 회원 탈퇴
	void deleteUser(String userId) throws Exception;
	
	// 로그인
	UserDto loginUser(UserDto userDto) throws Exception;
	
	// DB에 Refresh Token 저장
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	
	// DB에서 Refresh Token 삭제
	void deleteRefreshToken(String userId) throws Exception;
	
	// Refresh Token 반환
	Object getRefreshToken(String userId) throws Exception;
}
