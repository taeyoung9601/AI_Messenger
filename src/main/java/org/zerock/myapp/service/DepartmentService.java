package org.zerock.myapp.service;

import org.zerock.myapp.domain.DepartmentDTO;

public interface DepartmentService {
	
	public abstract DepartmentDTO getByIdAndEnabled(Long id, Boolean b);    // 단일 조회
	
}//end interface