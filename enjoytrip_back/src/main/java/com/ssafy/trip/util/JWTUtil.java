package com.ssafy.trip.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

//# JWT setting
//jwt.salt=ssafy-screte-key-cbg-bsy
//#1 hour (millis)
//jwt.access-token.expiretime=3600000
//# 30 days (millis)
//jwt.refresh-token.expiretime=2592000000

@Component
@Slf4j
public class JWTUtil {
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.access-token.expiretime}")
	private long accessToken;
	
	@Value("${jwt.refresh-token.expiretime}")
	private long refreshToken;
	
	// Access Token 발급
	public String createAccessToken(String userId) {
		return create(userId, "access-token", accessToken);
	}
	
	// Refresh Token 발급 : Access Token에 비해 유효기간 길게 설정
	public String createRefreshToken(String userId) {
		return create(userId, "refresh-token", refreshToken);
	}
	
	// Token 발급
	public String create(String userId, String subject, long expireTime) {
		
		// Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration), 토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
		Claims claims = Jwts.claims()
				.setSubject(subject) // 토큰 제목
				.setIssuedAt(new Date()) // 생성일
				.setExpiration(new Date(System.currentTimeMillis() + expireTime)); // 만료일
		claims.put("userId", userId); // 저장할 데이터

		// Token 생성
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT") // Header 설정
				.setClaims(claims) // Payload 설정
				.signWith(SignatureAlgorithm.HS256, this.generateKey()) // Signature 설정
				.compact(); // 직렬화
		return jwt;
	}
	
	// Signature 설정에 들어갈 Key 생성 : 바이트 배열 변환
	private byte[] generateKey() {
		byte[] key = null;
		try {
			// SignatureKey 문자열을 바이트 배열로 변환
			// charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩
			key = salt.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (log.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		return key;
	}

	// Token 유효성 확인
	public boolean checkToken(String token) {
		try {
			// Json Web Signature? 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
			// setSigningKey : JWS 서명 검증을 위한  secret key 세팅
			// parseClaimsJws : 파싱하여 원본 jws 만들기
			// 위조되지 않은 token이라면 정상적으로 jws가 반환되지만, 그렇지 않다면 오류가 발생한다
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
			log.debug("claims : {}", claims);
			return true;
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}
}
