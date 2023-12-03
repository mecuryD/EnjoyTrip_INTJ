package com.ssafy.trip.exception;

public class UnAuthorizedException extends RuntimeException {
	
	// 예외 발생 원인(예외 메시지)을 전달
	public UnAuthorizedException() {
		super("계정 권한이 유효하지 않습니다. \n다시 로그인을 시도해주세요.");
	}
}
