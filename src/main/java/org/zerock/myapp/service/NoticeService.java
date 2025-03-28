package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.NoticeDTO;
import org.zerock.myapp.entity.Notice;

public interface NoticeService {
	
	public abstract List<Notice> getAllList();     			// 전체 조회
	public abstract List<Notice> getSearchList(NoticeDTO dto); // 전체 조회(검색)
	
	public abstract Notice create(NoticeDTO dto);    	// 생성 처리
	public abstract Notice getById(String id);    // 단일 조회
	public abstract Boolean update(NoticeDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
}//end interface