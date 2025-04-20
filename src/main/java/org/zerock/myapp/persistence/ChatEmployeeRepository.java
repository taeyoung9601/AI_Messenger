package org.zerock.myapp.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;


/**
 * 채팅 Repository
 */

@Repository
public interface ChatEmployeeRepository extends JpaRepository<ChatEmployee, ChatEmployeePK>, JpaSpecificationExecutor<Chat> {
	public abstract List<ChatEmployee> findByIdChatId(Long chatId);
	public abstract List<ChatEmployee> findByEnabledAndIdChatId(Boolean b, Long chatId);
	public abstract List<ChatEmployee> findByEnabledAndIdEmpno(Boolean b, String empno);
	public abstract ChatEmployee findByIdChatIdAndIdEmpno(Long chatId, String empno);
	
	//게시판 별 유효한 전체 게시물 + 작성자
	final String nativeSQL_board_empname = """
			SELECT DISTINCT C.*
			FROM T_ChatEmployee C
			   LEFT JOIN T_Message M ON C.EMPNO = M.EMPNO
			WHERE C.ENABLED = :enabled
			ORDER BY MESSAGE.CRTDATE
		""";
	@Query(value = nativeSQL_board_empname, nativeQuery = true)
	List<ChatEmployee> findChatByEmpno(
		@Param("enabled") Boolean enabled,
		@Param("empno") String empno
	);
} // end interface
