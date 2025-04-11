package org.zerock.myapp.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.persistence.ChatEmployeeRepository;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@Transactional

@SpringBootTest
public class ChatServiceTest {

	 	@Autowired private ChatService chatService;
	    @Autowired private EmployeeRepository employeeRepository;
	    @Autowired private ChatRepository chatRepository;
	    @Autowired private ChatEmployeeRepository chatEmployeeRepository;
	    
	    
//		@Disabled
		@Tag("Chat-Test")
		@Order(1)
		@Test
//		@RepeatedTest(1)
		@DisplayName("1. create")
		@Timeout(value = 1L, unit = TimeUnit.SECONDS)
		@Rollback(false)
	    void create() {
			
			ChatDTO chatDto = new ChatDTO(1L,"채팅방",true,new Date(), new Date(), null,null,null,null,null);
			
			String existingEmployeeId = "Nonna Esselin"; // 예시: DB에 미리 존재하는 직원 ID
		    Employee employee = employeeRepository.findById(existingEmployeeId)
		        .orElseThrow(() -> new RuntimeException("직원이 존재하지 않습니다."));
		    
		    ChatEmployeePK pk = new ChatEmployeePK();
		    pk.setEmpno(employee.getEmpno());          // 기존 직원 ID
		    
	        // given
	    	Chat chat = new Chat();
	        chat.setName("채팅방");
	        chat.setChatEmployees(null);
	        chat.setProject(null);	

	        chat = chatRepository.save(chat);

	        for (ChatEmployee chatEmployee : chatDto.getChatEmployees()) {
	            Employee emp = employeeRepository.findById(chatEmployee.getId().getEmpno())
	                .orElseThrow(() -> new RuntimeException("사원 없음"));

	            ChatEmployeePK pk = new ChatEmployeePK();
	            pk.setEmpno(emp.getEmpno());
	            pk.setChatId(chat.getId());
	            ChatEmployee cEmployee = new ChatEmployee();
	            cEmployee.setId(pk);
	            cEmployee.setChat(chat);
	            cEmployee.setEmployee(emp);

	            chatEmployeeRepository.save(cEmployee);
	        }

//	        return chat;
	        
	    }
	
}
