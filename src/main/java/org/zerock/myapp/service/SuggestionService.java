package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.SuggestionDTO;
import org.zerock.myapp.entity.Suggestion;

public interface SuggestionService {
	
	public abstract List<Suggestion> getAllList();     			// 전체 조회
	public abstract List<Suggestion> getSearchList(SuggestionDTO dto); // 전체 조회(검색)
	
	public abstract Suggestion create(SuggestionDTO dto);    	// 생성 처리
	public abstract Suggestion getById(String id);    // 단일 조회
	public abstract Boolean update(SuggestionDTO dto);    	// 수정 처리
	public abstract Boolean deleteById(String id);// 삭제 처리
	
}//end interface