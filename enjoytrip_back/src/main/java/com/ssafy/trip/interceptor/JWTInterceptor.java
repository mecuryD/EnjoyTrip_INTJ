package com.ssafy.trip.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.trip.exception.UnAuthorizedException;
import com.ssafy.trip.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

	// Client Header에서 Authorization
	private final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// Authorization(인가): Client 요청을 Controller에 넘겨주기 전에 권한 여부를 확인한다
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		// Client Request에서 Token Get
		final String token = request.getHeader(HEADER_AUTH);
		
		if(token != null && jwtUtil.checkToken(token)) {
			log.info("토큰 사용 가능 : {}", token);
			return true;
		}else {
			log.info("토큰 사용 불가능 : {}", token);
			throw new UnAuthorizedException();
		}
	}
}
