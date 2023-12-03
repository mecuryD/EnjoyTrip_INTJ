package com.ssafy.trip.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.model.UserDto;
import com.ssafy.trip.user.model.service.UserService;
import com.ssafy.trip.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@Api(tags = {"회원 컨트롤러 API V1"})
@Slf4j
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	private JWTUtil jwtUtil;
	
	@Autowired
	public UserController(UserService userService, JWTUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil  = jwtUtil;
	}
	
	// 회원 가입 
	@ApiOperation(value = "회원 가입", notes = "회원 정보를 등록합니다.")
	@PostMapping
	public ResponseEntity<String> userRegist(@RequestBody UserDto userDto) throws Exception{
		System.out.println(userDto.toString());
//		log.info("userId : {}", userDto.getUserId());
//		log.info("userPwd : {}", userDto.getUserPwd());
//		log.info("userLevel : {}", userDto.getLevel());
		userService.registUser(userDto);
		return ResponseEntity.ok().build();
	}
	
	// 회원 탈퇴
	@ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 삭제합니다.")
	@DeleteMapping(value="/{userId}")
	public ResponseEntity<String> userDelete(@PathVariable("userId") String userId) throws Exception{
		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
	
	// 모든 회원 정보조회
	@ApiOperation(value = "회원 정보 전체 조회", notes = "전체 회원 정보를 조회합니다.")
	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> userInfoList() throws Exception{
		return ResponseEntity.ok().body(userService.listUser());
	}
	
	// 단일 회원 정보 조회
	@ApiOperation(value = "회원 정보 상세 조회", notes = "회원 상세 정보를 조회합니다.")
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> userInfo(@PathVariable("userId") String userId) throws Exception{
		return ResponseEntity.ok().body(userService.getUser(userId));
	}
	
	// 회원 정보 수정
	@ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정합니다.")
	@PutMapping("/modify")
	public ResponseEntity<UserDto> userModify(@RequestBody UserDto userDto) throws Exception{
		System.out.println(userDto);
		userService.modifyUser(userDto);
		return ResponseEntity.ok().body(userService.getUser(userDto.getUserId()));
	}
	
	// 로그인
	@ApiOperation(value = "로그인", notes = "로그인 여부에 따라 Refresh Token, Access Token을 발급합니다")
	@PostMapping(value="/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDto userDto) throws Exception{
		log.debug("login user : {}", userDto);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(); // Client에 반환할 데이터 맵
		HttpStatus status = HttpStatus.ACCEPTED;
		
		// 로그인 시도		
		UserDto loginDto = userService.loginUser(userDto);
		if(loginDto != null) {
			// 로그인 정보가 올바르다면, 토큰 발행
			String accessToken = jwtUtil.createAccessToken(loginDto.getUserId());
			String refreshToken = jwtUtil.createRefreshToken(loginDto.getUserId());
			log.debug("발행된 access token : {}", accessToken);
			log.debug("발행된 refresh token : {}", refreshToken);
			
			// 발급 받은 refresh token을 DB에 저장
			userService.saveRefreshToken(loginDto.getUserId(), refreshToken);
			
			// Client에게 JSON으로 token 전달
			resultMap.put("access-token", accessToken);
			resultMap.put("refresh-token", refreshToken);
			status = HttpStatus.CREATED;
		}else {
			// 로그인 정보가 올바르지 않다면, 경고 메시지 전달
 			status = HttpStatus.UNAUTHORIZED;
		}
		
		return ResponseEntity.status(status).body(resultMap);
	}

	// 로그아웃
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다")
	@GetMapping(value="/logout/{userId}")
	public ResponseEntity<Map<String,Object>> logout(
			@PathVariable("userId") @ApiParam(value="로그아웃 할 회원의 아이디", required = true) String userId) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		// 로그아웃 시도
		try {
			userService.deleteRefreshToken(userId);
			// 로그인 성공 시, 처리 완료 상태 전달
			status = HttpStatus.OK;
		}catch(Exception e) {
			// 로그아웃 실패 시, 서버 에러 전달
			log.error("로그아웃 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			resultMap.put("message", e.getMessage());
		}
		
		return ResponseEntity.ok().body(resultMap);
	}

	// Access Token 재발급
	@ApiOperation(value = "Access Token 재발급", notes = "Refresh Token으로 만료된 Access Token을 다시 생성하여 반환")
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		// Client에 전달할 상태 코드 및 Map
		HttpStatus status = HttpStatus.ACCEPTED;
		Map<String, Object> resultMap = new HashMap<>();
		
		// Refresh Token 받아오기
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, userDto);
		
		// Refresh Token 유효성 확인해 Access Token 재발급
		if(jwtUtil.checkToken(token)) { // Token이 유효하고
			if(token.equals(userService.getRefreshToken(userDto.getUserId()))) { // 사용자도 일치하면
				String accessToken = jwtUtil.createAccessToken(userDto.getUserId());
				log.debug("정상적으로 Access Token 발급  !!");
				log.debug("token : {}", accessToken);
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		}else { // Token 유효하지 않음
			log.debug("유효하지 않은 Refresh Token !!");
			log.debug("token : {}", token);
			status = HttpStatus.UNAUTHORIZED;
		}
		return ResponseEntity.status(status).body(resultMap);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{userId}")
    public ResponseEntity<Map<String, Object>> getInfo(    @PathVariable("userId") String userId, HttpServletRequest request) {
        // Client에 넘겨줄 상태 코드와 Map
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        
        // 토큰 유효성 확인
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
            log.info("사용 가능한 토큰!!!");
            try {
                // 로그인 사용자 정보.
                UserDto memberDto = userService.getUser(userId);
                resultMap.put("userInfo", memberDto);
                status = HttpStatus.OK;
            } catch (Exception e) {
                log.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            log.error("사용 불가능 토큰!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
