package org.zerock.myapp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.entity.Employee;


/**
 * 게시판(공지사항 + 건의사항) Repository
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {

	public abstract Optional<Board> findByTitle(String title);
	public abstract Optional<Board> findByEmployee (Employee employee);
	public abstract Optional<Board> findByType(Integer Type);
	
} // end interface
