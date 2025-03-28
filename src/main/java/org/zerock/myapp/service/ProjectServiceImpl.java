package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.ProjectDTO;
import org.zerock.myapp.entity.Project;
import org.zerock.myapp.persistence.ProjectRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired ProjectRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("ProjectServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Project> getAllList() {	//검색 없는 전체 리스트
		log.debug("ProjectServiceImpl -- getAllList() invoked");
		
		List<Project> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Project> getSearchList(ProjectDTO dto) {	//검색 있는 전체 리스트
		log.debug("ProjectServiceImpl -- getSearchList(()) invoked", dto);

		List<Project> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Project create(ProjectDTO dto) {	//등록 처리
		log.debug("ProjectServiceImpl -- create({}) invoked", dto);
		
		Project data = new Project();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Project getById(String id) {	// 단일 조회
		log.debug("ProjectServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Project data = new Project();//dao.findById(id).orElse(new Project());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(ProjectDTO dto) {//수정 처리
		log.debug("ProjectServiceImpl -- update({}) invoked", dto);
		
//		Project data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("ProjectServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
