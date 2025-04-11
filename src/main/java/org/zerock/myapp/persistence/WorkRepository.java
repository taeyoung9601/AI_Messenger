package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Work;


/**
 * 업무 Repository
 */

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>, JpaSpecificationExecutor<Work> {
	;;
} // end interface
