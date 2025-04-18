package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.DepartmentRepository;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.util.DateTimeUtils;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired EmployeeRepository dao;
    @Autowired DepartmentRepository departmentRepository;
//    @Autowired BCryptPasswordEncoder bcrypt;

    
    
	@PostConstruct
    void postConstruct(){
        log.debug("EmployeeServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Employee> getAllList() {	//검색 없는 전체 리스트
		log.debug("EmployeeServiceImpl -- getAllList() invoked");
		
		List<Employee> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Employee> getSearchList(EmployeeDTO dto) {	//검색 있는 전체 리스트
		log.debug("EmployeeServiceImpl -- getSearchList(()) invoked", dto);

		List<Employee> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public List<Employee> getPositionsList() {
		
		Integer[] position =  { 2, 3};
		List<Employee> positionList = this.dao.findByEnabledAndPositionInOrderByPositionAscCrtDateDesc(true, position);
		
		return data;
	} // create
	
	@Override
	public Employee getById(String id) {	// 단일 조회
		log.debug("EmployeeServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Employee data = new Employee();//dao.findById(id).orElse(new Employee());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(EmployeeDTO dto) {//수정 처리
		log.debug("EmployeeServiceImpl -- update({}) invoked", dto);
		
//		Employee data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("EmployeeServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
