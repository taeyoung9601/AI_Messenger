package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.EmployeeDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.service.EmployeeService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 사원 관리 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/employee")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	@Autowired private EmployeeService empService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployees() {
	    List<Employee> list = empService.getAllList();
	    return ResponseEntity.ok(list);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Employee>> searchEmployees(
	        @RequestParam String searchWord,
	        @RequestParam String searchText
	) {
	    EmployeeDTO dto = new EmployeeDTO();
	    dto.setSearchWord(searchWord);
	    dto.setSearchText(searchText);

	    List<Employee> result = empService.getSearchList(dto);
	    return ResponseEntity.ok(result);
	}
	
	@PostMapping
	String register() { // 등록 처리
		log.debug("register() invoked.");
		
		return "register";
	} // register
	
	@GetMapping(path = "/{id}")
	String read( // 세부 조회
			@PathVariable Long id
			) {
		log.debug("read({}) invoked.",id);
		
		return "read";
	} // read
	
	@PutMapping(path = "/{id}")
	String update( // 수정 처리
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return "update";
	} // update
	
	@DeleteMapping(path = "/{id}")
	String delete( // 삭제 처리
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return "delete";
	} // delete

} // end class
