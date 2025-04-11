package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.ChatEmployeeRepository;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class ChatServiceImpl implements ChatService {
	
    @Autowired ChatRepository chatRepository;
    @Autowired ChatEmployeeRepository chatEmployeeRepository;
    @Autowired EmployeeRepository employeeRepository;
	
	@PostConstruct
    void postConstruct(){
        log.debug("ChatServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", chatRepository);
    }//postConstruct


	@Override
	public List<Chat> findAllList() {	//검색 없는 전체 리스트
		log.debug("ChatServiceImpl -- getAllList() invoked");
		
		List<Chat> list = this.chatRepository.findAllByEnabled(true);
		
		return list;
	} // getAllList
	
	@Override
	public List<Chat> getSearchList(ChatDTO dto) {	//검색 있는 전체 리스트
		log.debug("ChatServiceImpl -- getSearchList(()) invoked", dto);

		List<Chat> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Chat createRoom(ChatDTO dto) {	//등록 처리
		log.debug("ChatServiceImpl -- create({}) invoked");
		
		 // 받아온 dto로 해당 채팅 만들기
		Chat chat = new Chat();
        chat.setName(dto.getName());
        chat.setChatEmployees(dto.getChatEmployees());
        chat.setProject(dto.getProject());	

//        chat = chatRepository.save(chat); 이건 완료
        
        // 생성시 초대한 사원들을 채팅방-사원 테이블에 저장
        // 현재 ChatEmployee에 null이라 오류
        for (ChatEmployee chatEmployee : dto.getChatEmployees()) {
            Employee emp = employeeRepository.findById(chatEmployee.getEmployee().getEmpno())
                .orElseThrow(() -> new RuntimeException("사원 없음"));

            ChatEmployeePK pk = new ChatEmployeePK();
            pk.setEmpno(emp.getEmpno());
            pk.setChatId(chat.getId());
            ChatEmployee cEmployee = new ChatEmployee();
            cEmployee.setId(pk);
//            cEmployee.setChat(chat);
//            cEmployee.setEmployee(emp);

            chatEmployeeRepository.save(cEmployee);
        }

        return chat;
        
    
	} // create
	
	@Override
	public Chat getById(Long id) {	// 단일 조회
		log.debug("ChatServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Chat data = new Chat();//dao.findById(id).orElse(new Chat());
		
		data = this.chatRepository.findById(id).orElse(new Chat());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(ChatDTO dto) {//수정 처리
		log.debug("ChatServiceImpl -- update({}) invoked", dto);

	    Chat chat = this.chatRepository.findById(dto.getId())
	        .orElseThrow(() -> new EntityNotFoundException("채팅방이 존재하지 않습니다."));

	    chat.setName(dto.getName());
	    chat.setUdtDate(new Date());
	    
	    this.chatRepository.save(chat);
	    
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(Long id) { // 삭제 처리
		log.debug("ChatServiceImpl -- deleteById({}) invoked", id);
		
		Chat chat = this.chatRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("채팅방이 존재하지 않습니다."));;
		
		chat.setEnabled(false);
		
		this.chatRepository.save(chat);
		
		return true;
	} // deleteById
	
	
	
}//end class
