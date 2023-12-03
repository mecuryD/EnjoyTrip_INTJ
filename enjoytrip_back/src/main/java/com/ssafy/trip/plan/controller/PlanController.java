package com.ssafy.trip.plan.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.plan.model.PlanDto;
import com.ssafy.trip.plan.model.PlanInfoDto;
import com.ssafy.trip.plan.model.service.PlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/plan")
@CrossOrigin("*")
@Api(tags = { "여행 계획 컨트롤러  API V1" })
@Slf4j
public class PlanController {

	@Autowired
	private PlanService planService;

	// 여행 계획 등록
	@ApiOperation(value = "여행 계획 등록", notes = "새로운 여행 계획을 등록합니다")
	@PostMapping
	public ResponseEntity<Objects> registPlan(@RequestBody PlanDto planDto) throws Exception {
		log.info("registPlan - 호출");
		try {
			// Client에서 넘어온 데이터
//			RequestBody를 Map 타입으로 받는 경우
//			ObjectMapper mapper = new ObjectMapper();
//			PlanDto plan = mapper.convertValue(newPlan.get("plan"), PlanDto.class);
//			PlanInfoDto planInfo = mapper.convertValue(newPlan.get("planInfo"), PlanInfoDto.class); // list
			
			// DB 저장
			planService.registPlan(planDto, planDto.getPlanInfos());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.info("error : {}", e);
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// 여행 계획 목록
	@ApiOperation(value = "여행 계획 목록", notes = "여행 계획 전체를 불러옵니다")
	@GetMapping(value = { "/all"})
	public ResponseEntity<List<PlanDto>> listPlan() {
		log.info("listPlan - 호출");
		try {
			// DB 쿼리
			return ResponseEntity.ok().body(planService.listPlan());
		} catch (Exception e) {
			log.info("error : {}", e);
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// 내 여행 계획 목록
	@ApiOperation(value = "내 여행 계획 목록", notes = "내가 등록한 여행 계획을 불러옵니다")
	@GetMapping(value = "/all/{userId}")
	public ResponseEntity<List<PlanDto>> listMyPlan(@PathVariable(required = false) String userId) {
		log.info("listMyPlan - 호출");
		try {
			// DB 쿼리
			return ResponseEntity.ok().body(planService.listMyPlan(userId));
		} catch (Exception e) {
			log.info("error : {}", e);
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// 여행 계획 삭제 : CASCADE 제약 조건으로 planInfo도 함께 삭제된다
	@ApiOperation(value = "여행 계획 삭제", notes = "해당 여행 계획을 삭제합니다")
	@DeleteMapping(value="/{planId}")
	public ResponseEntity<Objects> deletePlan(@PathVariable int planId){
		log.info("deletePlan - 호출");
		try {
			// DB 삭제
			planService.deletePlan(planId);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			log.info("error : {}", e);
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// 여행 계획 상세 조회
	@ApiOperation(value = "여행 계획 상세 조회", notes = "해당 여행 계획 정보를 가져옵니다 (계획 소개 및 경로)")
	@GetMapping("/{planId}")
	public ResponseEntity<PlanDto> detailPlan(@PathVariable int planId){
		log.info("detailPlan - 호출");
		try {
			// DB 조회
			return ResponseEntity.ok().body(planService.detailPlan(planId));
		}catch(Exception e) {
			log.info("error : {}", e);
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	// 여행 계획 수정
	@ApiOperation(value = "여행 계획 수정", notes = "해당 여행 계획 정보를 가져옵니다 (계획 소개 및 경로)")
	@PutMapping("/modify")
	public ResponseEntity<Objects> modifyPlan(@RequestBody PlanDto planDto){
		log.info("modifyPlan - 호출");
		try {
			planService.deletePlan(planDto.getPlanId()); // 기존 PLAN 삭제 후
			planService.registPlan(planDto, planDto.getPlanInfos()); // 새로 PLAN 추가
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			log.info("error : {}", e);
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
