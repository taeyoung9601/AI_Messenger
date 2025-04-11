package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.entity.Work;
import org.zerock.myapp.service.WorkService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 업무 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/work")
@RestController
public class WorkController {
	@Autowired private WorkService workService;
	
	@GetMapping
	List<Work> list(
			WorkDTO dto
			) { // 리스트
		log.debug("list() invoked.");
		
		List<Work> works = workService.getAllList();

		works.forEach(s -> log.info(s.toString()));
		
		return works;
	} // list
	
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
