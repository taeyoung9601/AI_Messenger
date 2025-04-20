package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.ChatDTO;
import org.zerock.myapp.entity.Chat;

public interface ChatService {
	
	public abstract List<Chat> findMyList(String empno);			// 내 채팅방 리스트
	public abstract Chat createRoom(ChatDTO dto, String empno);    	// 생성 처리
	public abstract ChatDTO getById(Long id);    // 단일 조회
	public abstract Boolean update(ChatDTO dto, Long id);    	// 수정 처리
	public abstract Chat deleteById(Long id,String empno);// 삭제 처리
	
}//end interface