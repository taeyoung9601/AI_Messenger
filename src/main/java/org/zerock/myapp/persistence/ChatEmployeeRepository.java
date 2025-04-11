package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;


/**
 * 채팅 Repository
 */

@Repository
public interface ChatEmployeeRepository extends JpaRepository<ChatEmployee, ChatEmployeePK>, JpaSpecificationExecutor<Chat> {
//	List<ChatEmployee> findByIdChatId(Long chatId);
//	List<ChatEmployee> findByIdEmployeeId(Long employeeId);
	
} // end interface
