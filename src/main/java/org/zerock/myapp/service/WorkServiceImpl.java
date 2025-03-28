package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.entity.Work;
import org.zerock.myapp.persistence.WorkRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired WorkRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("WorkServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Work> getAllList() {	//검색 없는 전체 리스트
		log.debug("WorkServiceImpl -- getAllList() invoked");
		
		List<Work> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Work> getSearchList(WorkDTO dto) {	//검색 있는 전체 리스트
		log.debug("WorkServiceImpl -- getSearchList(()) invoked", dto);

		List<Work> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Work create(WorkDTO dto) {	//등록 처리
		log.debug("WorkServiceImpl -- create({}) invoked", dto);
		
		Work data = new Work();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Work getById(String id) {	// 단일 조회
		log.debug("WorkServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Work data = new Work();//dao.findById(id).orElse(new Work());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(WorkDTO dto) {//수정 처리
		log.debug("WorkServiceImpl -- update({}) invoked", dto);
		
//		Work data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("WorkServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
