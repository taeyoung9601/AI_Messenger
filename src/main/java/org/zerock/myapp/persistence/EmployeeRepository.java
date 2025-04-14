package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Employee;

/**
 * 회원 Repository
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

	// 회원가입시 똑같은 아이디가 db에 저장되어 있는지 검증.
	public abstract Boolean existsByLoginId(String loginId);

	// 사번 생성기 ( 테스트 중 )

	// int countByEmployeeCodeStartingWith(String prefix);
	// 여기서의 String prefix : 쿼리 메소드의 매개변수. entity 나 dto 에 선언할 필요 X

} // end interface
