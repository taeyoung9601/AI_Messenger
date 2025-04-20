package org.zerock.myapp.controller.common;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
@Data
public class JwtPrincipal implements UserDetails {
	
	private final String empno;
	private final String role;
	private final String name;
	private final String loginId;
	private final String password;
	private final String tel;
	private final String address;
	private final Integer zipCode;
	private final String email;
	private final Integer position;

// 🔐 권한 정보 반환 (Spring Security 필수)

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return List.of(new SimpleGrantedAuthority(role));
		
		}

	// ✅ 사용자 ID 기준 (Spring이 여기에 username 기반 검증함)
	
	@Override
	public String getUsername() {
	return this.empno; // 또는 loginId
	}
	
	@Override public boolean isAccountNonExpired() { return true; }
	@Override public boolean isAccountNonLocked() { return true; }
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }

}

// 사용 예제

//@GetMapping("/me")

//public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal JwtPrincipal principal) {

//    return ResponseEntity.ok("사번: " + principal.getEmpno() +

//                             ", 이름: " + principal.getUsername() +

//                             ", 권한: " + principal.getRole());

//}

// 하나만 꺼낼때.

//@GetMapping("/empno")

//public ResponseEntity<String> getEmpno(@AuthenticationPrincipal JwtPrincipal principal) {

//    return ResponseEntity.ok(principal.getEmpno());

//}