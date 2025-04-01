package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.FileDTO;
import org.zerock.myapp.entity.File;

public interface FileService {
	
	public abstract List<File> getAllList();     			// 전체 조회
	public abstract List<File> getSearchList(FileDTO dto); // 전체 조회(검색)
	
	public abstract File create(FileDTO dto);    	// 생성 처리
	public abstract File getById(String id);    // 단일 조회
	public abstract Boolean update(FileDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
}//end interface