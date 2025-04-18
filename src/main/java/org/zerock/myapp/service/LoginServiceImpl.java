package org.zerock.myapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {

	
	@Autowired LoginRepository loginRepo;
	@Autowired BCryptPasswordEncoder bcrypt;
	
	@Override
	public Optional<Employee> findByLoginId(String loginId) {
		return loginRepo.findByLoginId(loginId);
		
	}  // 사용자의 아이디 가 db 에 저장이 되어 있는지 검색

	@Override
	public Boolean checkPassword(String rawPassword, String encodedPassword) {
	    return bcrypt.matches(rawPassword, encodedPassword);
	} // 비번이 일치하는지 검증.

	@Override
	public Optional<Employee> login(String loginId, String password) {
		
		System.out.println("받은 아이디: " + loginId);
		System.out.println("받은 비밀번호: " + password);
		
		Optional<Employee> loginOptional = loginRepo.findByLoginId(loginId);
		
		if (loginOptional.isEmpty()) {
			throw new IllegalArgumentException("아이디 또는 패스워드를 입력해주세요.");
		} // 아이디 & 비밀번호 창이 비어있는 경우 검증
		
		Employee employee = loginOptional.get();
		
		// checkPassword = bcryptPasswordEncoder.matches 의 rapper 메소드
		if (! bcrypt.matches(password, employee.getPassword())) {
			throw new IllegalArgumentException("아이디 또는 패스워드가 틀립니다.");
		} // 비밀번호 검증.
		
	
		return loginOptional;
		
	} // end login
}
