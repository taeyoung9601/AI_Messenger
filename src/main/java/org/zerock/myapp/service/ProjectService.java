package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;

public interface ProjectService {
	
	public abstract List<Project> getAllList();     				// 전체 조회
	public abstract List<Project> getSearchList(ProjectDTO dto); 	// 전체 조회(검색)
	
	public abstract Project create(ProjectDTO dto);    				// 생성 처리
	public abstract Project getById(String id);    					// 단일 조회
	public abstract Boolean update(ProjectDTO dto);    				// 수정 처리
	public abstract Boolean deleteById(String id);					// 삭제 처리
	
}//end interface