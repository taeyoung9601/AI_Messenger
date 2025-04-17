package org.zerock.myapp.controller;

import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.domain.TokenResponseDto;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.service.JwtProvider;
import org.zerock.myapp.service.LoginServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@RestController
public class LoginController {

	
	@Autowired LoginServiceImpl service;
	@Autowired JwtProvider jwt;
	
	@PostMapping("/login")
	ResponseEntity<?> login( // ResponseEntity<?> :Spring 에서 http 응답 전체를 커스터마이징 하고 싶을때 사용하는 클래
			@ModelAttribute EmployeeDTO dto
	) {
	
	try {
	    Optional<Employee> employeecheck = service.login(dto.getLoginId(), dto.getPassword());

	    if (employeecheck.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패. 인증된 사용자가 아닙니다.");
	    }

	    Employee employee = employeecheck.get();
	    String token = JwtProvider.generateToken(dto.getLoginId(), employee);  // 여기서 생성
	    long expiresAt = System.currentTimeMillis() + 3600 * 1000; // 1시간 후 만료.

	    TokenResponseDto response = new TokenResponseDto(token, expiresAt);
	    return ResponseEntity.ok(response);  // 토큰 클라이언트에 반환
	} catch (IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	} // end login
	
	@GetMapping("/myinfo")
	public ResponseEntity<?> getMyInfo(Authentication authentication) {
		String loginId = authentication.name();
		return ResponseEntity.ok("반가워요. " + loginId + "님 !");
	}
	

	
}
