package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Suggestion;


/**
 * 게시판 - 건의사항 Repository
 */

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long>, JpaSpecificationExecutor<Suggestion> {

} // end interface
