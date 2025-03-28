package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.NoticeDTO;
import org.zerock.myapp.entity.Notice;
import org.zerock.myapp.persistence.NoticeRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired NoticeRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("NoticeServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Notice> getAllList() {	//검색 없는 전체 리스트
		log.debug("NoticeServiceImpl -- getAllList() invoked");
		
		List<Notice> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Notice> getSearchList(NoticeDTO dto) {	//검색 있는 전체 리스트
		log.debug("NoticeServiceImpl -- getSearchList(()) invoked", dto);

		List<Notice> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Notice create(NoticeDTO dto) {	//등록 처리
		log.debug("NoticeServiceImpl -- create({}) invoked", dto);
		
		Notice data = new Notice();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Notice getById(String id) {	// 단일 조회
		log.debug("NoticeServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Notice()와 같은 기본값을 반환합니다.
		Notice data = new Notice();//dao.findById(id).orElse(new Notice());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(NoticeDTO dto) {//수정 처리
		log.debug("NoticeServiceImpl -- update({}) invoked", dto);
		
//		Notice data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("NoticeServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
