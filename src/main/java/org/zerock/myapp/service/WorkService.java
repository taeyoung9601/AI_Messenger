package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.entity.Work;

public interface WorkService {
	
	public abstract List<Work> getAllList();     			// 전체 조회
	public abstract List<Work> getSearchList(WorkDTO dto); // 전체 조회(검색)
	
	public abstract Work create(WorkDTO dto);    	// 생성 처리
	public abstract Work getById(String id);    // 단일 조회
	public abstract Boolean update(WorkDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
}//end interface