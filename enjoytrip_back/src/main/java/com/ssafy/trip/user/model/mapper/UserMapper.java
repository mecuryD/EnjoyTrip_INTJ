package com.ssafy.trip.user.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.user.model.UserDto;

@Mapper
public interface UserMapper {
	// 회원 가입 (회원 정보 등록)
	void registUser(UserDto userDto) throws SQLException;
	
	// 회원 탈퇴 (회원 정보 삭제)
	void deleteUser(String userId) throws SQLException;

	// 회원 정보 전체 조회
	List<UserDto> listUser() throws SQLException;
	
	// 회원 정보 상세 조회
	UserDto getUser(String userId) throws SQLException;
	
	// 회원 정보 수정
	void modifyUser(UserDto userDto) throws SQLException;
	
	// 로그인
	UserDto loginUser(UserDto userDto) throws SQLException;
	
	// DB에 Refresh Token 저장
	void saveRefreshToken(@Param("userId") String userId, @Param("refreshToken") String refreshToken) throws SQLException;
	
	// DB에서 Refresh Token 삭제
	void deleteRefreshToken(String userId) throws Exception;
	
	// Refresh Token 반환
	Object getRefreshToken(String userId) throws Exception;
}
