package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.SuggestionDTO;
import org.zerock.myapp.entity.Suggestion;
import org.zerock.myapp.persistence.SuggestionRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired SuggestionRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("SuggestionServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Suggestion> getAllList() {	//검색 없는 전체 리스트
		log.debug("SuggestionServiceImpl -- getAllList() invoked");
		
		List<Suggestion> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Suggestion> getSearchList(SuggestionDTO dto) {	//검색 있는 전체 리스트
		log.debug("SuggestionServiceImpl -- getSearchList(()) invoked", dto);

		List<Suggestion> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Suggestion create(SuggestionDTO dto) {	//등록 처리
		log.debug("SuggestionServiceImpl -- create({}) invoked", dto);
		
		Suggestion data = new Suggestion();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Suggestion getById(String id) {	// 단일 조회
		log.debug("SuggestionServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Suggestion()와 같은 기본값을 반환합니다.
		Suggestion data = new Suggestion();//dao.findById(id).orElse(new Suggestion());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(SuggestionDTO dto) {//수정 처리
		log.debug("SuggestionServiceImpl -- update({}) invoked", dto);
		
//		Suggestion data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("SuggestionServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
