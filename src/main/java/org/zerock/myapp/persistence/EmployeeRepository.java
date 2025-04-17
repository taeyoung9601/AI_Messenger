package org.zerock.myapp.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Department;
import org.zerock.myapp.entity.Employee;


/**
 * 회원 Repository
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

	Optional<Employee> findByEnabledAndEmpno(Boolean enabled, String empno);

	List<Employee> findByEnabledAndDepartment(Boolean b, Department entity);

	List<Employee> findByNameContainingAndEnabledTrue(String name);
	List<Employee> findByTelContainingAndEnabledTrue(String tel);
//	List<Employee> findByEmailContainingAndEnabledTrue(String email);
//	List<Employee> findByEmpnoContainingAndEnabledTrue(String empno);
	
	List<Employee> findByEnabledAndPositionIn (Boolean enabled, List<Integer> positions );
	
} // end interface
