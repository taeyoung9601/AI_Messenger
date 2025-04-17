package org.zerock.myapp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.service.MessageService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 메시지 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/message")
@RestController
public class MessageController {
	
	@Autowired private MessageService messageService;
	
	@GetMapping
	List<Message> ListByChatId(@RequestParam Long chatId) { // 리스트
		log.debug("delete({}) invoked.",chatId);
		
		return this.messageService.getByChatId(chatId);
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

	
	@GetMapping(path = "/{id}")
	String summarize( // 세부 조회
			@PathVariable Long id,
			Date start, Date end
			// 날짜 포함
			) {
		log.debug("summarize({}) invoked.",id);
		this.messageService.summarizeMessage(id, start, end);
		return "요약된 메시지";
	} // read
	
} // end class
