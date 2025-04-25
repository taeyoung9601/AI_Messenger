package org.zerock.myapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.handler.WebSocketChatHandler;
import org.zerock.myapp.persistence.ChatEmployeeRepository;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.ProjectRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;


//@Slf4j
@Log4j2
@NoArgsConstructor

@Service
public class ChatServiceImpl implements ChatService {
	
    @Autowired private ChatRepository chatRepository;
    @Autowired private ChatEmployeeRepository chatEmployeeRepository;
    @Autowired private EmployeeRepository employeeRepository;
	@Autowired private ProjectRepository projectRepository;
	@Autowired private WebSocketChatHandler webSocketChatHandler;
	
	@PostConstruct
    void postConstruct(){
        log.debug("ChatServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", chatRepository);
    }//postConstruct


	@Override
	public List<ChatEmployee> findMyList(String empno) {	//로그인한 사원이 속한 채팅방 리스트
		log.debug("ChatServiceImpl -- getAllList() invoked");
		
		List<ChatEmployee> myChatList = 
				this.chatEmployeeRepository.findChatByEmpno(true, empno);

		myChatList.forEach(log::info);
		
	    return myChatList;

	} // getAllList
	
	@Override
	public Chat createRoom(ChatDTO dto, String empno) {
	    log.debug("ChatServiceImpl -- createRoom({}) invoked", dto);
	    
	    try {
		    // 1. Chat 엔티티 생성 및 기본값 세팅
		    Chat chat = new Chat();
		    chat.setName(dto.getName());
		    if (dto.getProjectId() != null) {
		        Project project = projectRepository.findById(dto.getProjectId())
		            .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
		        chat.setProject(project);
		    } else {
		        chat.setProject(null);
		    }
		    
		    Chat savedChat = this.chatRepository.save(chat);
		    
//		     2. 채팅 생성자(자기자신)를 ChatEmployee로 추가 ( 토큰으로 가져오기)
	        Employee employee = employeeRepository.findById(empno)
	                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
	        
	        ChatEmployeePK pk = new ChatEmployeePK();
	        pk.setChatId(dto.getId());
	        pk.setEmpno(empno);
	        
	        ChatEmployee chatEmployee = new ChatEmployee();
	        chatEmployee.setId(pk);
	        chatEmployee.setChat(savedChat);
	        chatEmployee.setEmployee(employee);
	        this.chatEmployeeRepository.save(chatEmployee);

		    return chat;
	    } catch (Exception e) {
	    	log.error("Create failed: {}", e.getMessage(), e);
	    	throw new RuntimeException("채팅방 생성 중 오류 발생");
	    }
	   
	}// createRoom // 방 생성시 본인은 채팅방에 입장
	
	@Override
	public ChatDTO getById(Long id) {	// 단일 조회
		log.debug("ChatServiceImpl -- getById({}) invoked", id);
		
		Chat chat = this.chatRepository.findById(id).
				orElseThrow(() -> new RuntimeException("채팅방 없음"));
		
		ChatDTO dto = chat.toDTO();
		
		List<ChatEmployee> selectedChatEmployee = this.chatEmployeeRepository.findByIdChatId(id);
	
		dto.setChatEmployees(selectedChatEmployee);
		
		return dto;
	} // getById
	
	@Override
	public Boolean update(ChatDTO dto,Long id) {//수정 처리
		log.debug("ChatServiceImpl -- update({}) invoked", dto);

		try {
			for (String empno : dto.getEmpnos()) {
				ChatEmployee inviteEmp =
						this.chatEmployeeRepository.findByIdChatIdAndIdEmpno(dto.getId(),empno);
				
		        if (inviteEmp != null) {
		            // 이미 존재할 경우
		            if (!inviteEmp.getEnabled()) {
		                inviteEmp.setEnabled(true);
		                this.chatEmployeeRepository.save(inviteEmp);
		            }
		        }else{
					ChatEmployee chatEmployee = new ChatEmployee();
					
			        Employee emp = this.employeeRepository.findById(empno)
			            .orElseThrow(() -> new RuntimeException("사원 없음"));
		
			        // 복합키 생성
			        ChatEmployeePK pk = new ChatEmployeePK();
			        pk.setChatId(dto.getId());
			        pk.setEmpno(empno);
		
			        // ChatEmployee 및 세팅
			        chatEmployee.setId(pk);
			        chatEmployee.setChat(this.chatRepository.findById(dto.getId()).get());  
			        chatEmployee.setEmployee(emp);  
			        
			        // 저장
			        this.chatEmployeeRepository.save(chatEmployee);
				}// if- else
				
		    } // for
			
			return true;
	    } catch (Exception e) {
	    	log.error("Update failed: {}", e.getMessage(), e);
	    	return false;
	    } // try-catch
		
	} // update

	@Override
	public Chat deleteById(Long id, String empno){ // 삭제 처리
		log.debug("ChatServiceImpl -- deleteById({}) invoked", id);
		
		try {
			// 채팅방 나가기 기능
			ChatEmployee chatEmployee = this.chatEmployeeRepository.findByIdChatIdAndIdEmpno(id,empno);
			chatEmployee.setEnabled(false);
			this.chatEmployeeRepository.save(chatEmployee);
			
			// 사람이 아무도 없을 경우 채팅방 삭제
			Chat chat = this.chatRepository.findById(id).get();
			if(this.chatEmployeeRepository.findByEnabledAndIdChatId(true,id).isEmpty()) {
				chat.setEnabled(false);
				this.chatRepository.save(chat);
			} // if
	
			// 세션 종료
			Set<WebSocketSession> sessions = webSocketChatHandler.getChatRoomSessions().get(id);	
			if (sessions != null) {
			    for (WebSocketSession session : sessions) {
			        String sessionEmpno = (String) session.getAttributes().get("empno");

			        if (empno.equals(sessionEmpno)) {
			            try {
			                session.close();  // 세션 강제 종료
			                break;
			            } catch (IOException e) {
			                log.error("세션 종료 실패: {}", e.getMessage());
			            }
			        }
			    }
			}
			
			return chat;
		} catch(Exception e) {
			log.error("Delete failed: {}", e.getMessage(), e);
			throw new RuntimeException("채팅방 삭제 중 오류 발생");
		}
	} // deleteById  // 로그인한 본인이 퇴장 처리 (본인의 사번과 채팅방 id를 넘겨줘야함)
	
	
	@Override
    // 초대 처리
    public void inviteEmployee(Long chatId, String empno) {
        Employee employee = employeeRepository.findById(empno)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 초대 메시지 생성
        MessageDTO inviteMessage = new MessageDTO();
        inviteMessage.setDetail(employee.getName() + "님이 초대되었습니다.");
        inviteMessage.setType("INVITE");
        inviteMessage.setChatId(chatId);
        inviteMessage.setEmpno(empno);

        // WebSocket을 통해 초대 메시지 전송
        webSocketChatHandler.broadcastToChatRoom(chatId, inviteMessage);
    }

	@Override
    // 퇴장 처리
    public void leaveChat(Long chatId, String empno) {
        Employee employee = employeeRepository.findById(empno)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 퇴장 메시지 생성
        MessageDTO leaveMessage = new MessageDTO();
        leaveMessage.setDetail(employee.getName() + "님이 채팅방을 떠났습니다.");
        leaveMessage.setType("LEAVE");
        leaveMessage.setChatId(chatId);
        leaveMessage.setEmpno(empno);

        // WebSocket을 통해 퇴장 메시지 전송
        webSocketChatHandler.broadcastToChatRoom(chatId, leaveMessage);
    }
}//end class