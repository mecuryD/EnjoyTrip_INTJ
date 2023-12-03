package com.ssafy.trip.user.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.mapper.UserMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	// 회원 가입
	@Override
	public void registUser(UserDto userDto) throws Exception {
		userMapper.registUser(userDto);		
	}
	
	// 회원 탈퇴
	@Override
	public void deleteUser(String userId) throws Exception {
		userMapper.deleteUser(userId);
	}

	// 전체 회원 정보 조회
	@Override
	public List<UserDto> listUser() throws Exception {
		return userMapper.listUser();
	}

	// 단일 회원 정보 조회
	@Override
	public UserDto getUser(String userId) throws Exception {
		return userMapper.getUser(userId);
	}

	// 회원 정보 수정
	@Override
	public void modifyUser(UserDto userDto) throws Exception {
		userMapper.modifyUser(userDto);
	}

	// 로그인
	@Override
	public UserDto loginUser(UserDto userDto) throws Exception {
		return userMapper.loginUser(userDto);		
	}

	// DB에 Refresh Token 저장
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		userMapper.saveRefreshToken(userId, refreshToken);
	}
	
	// DB에서 Refresh Token 삭제
	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		userMapper.deleteRefreshToken(userId);
	}
	
	// Refresh Token 반환
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}
	
}
