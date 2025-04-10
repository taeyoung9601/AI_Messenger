package org.zerock.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.service.ChatService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 채팅 Controller
 */

@Slf4j
@NoArgsConstructor

@RequestMapping("/chat")
@RestController
public class ChatController {
		
	@Autowired private ChatService ChatService;
	
	
	@GetMapping(path="/room2")
	ModelAndView  room2() { // 룸2
		log.debug("room2() invoked.");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("room2"); // "room2.html" 템플릿 파일 렌더링
		return mav;
	} // room2
	
	
	@GetMapping
	List<Chat> list() { // 리스트
		log.debug("list() invoked.");
		
		return ChatService.findAllList();
	} // list
	
	@PostMapping
	Chat register(@RequestBody ChatDTO dto) { // 등록 처리
		log.debug("register() invoked.");
		
		return ChatService.createRoom(dto);
	} // register
	
	@GetMapping(path = "/{id}")
	Chat read( // 세부 조회
			@PathVariable Long id
			) {
		log.debug("read({}) invoked.",id);
		
		return ChatService.getById(id);
	} // read
	
	@PutMapping(path = "/{id}")
	Boolean update( // 수정 처리
			ChatDTO dto,
			@PathVariable Long id
			) { 
		log.debug("update({}) invoked.",id);
		
		return ChatService.update(dto);
	} // update
	
	@DeleteMapping(path = "/{id}")
	Boolean delete( // 삭제 처리
			@PathVariable Long id
			) {
		log.debug("delete({}) invoked.",id);
		
		return ChatService.deleteById(id);
	} // delete
	

} // end class
