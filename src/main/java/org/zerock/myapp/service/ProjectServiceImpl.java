package org.zerock.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        log.debug("\t+ ProjectServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", this.dao);
    }//postConstruct
	
	
	@Override
	public Page<Project> getSearchList(ProjectDTO dto, Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getSearchList(()) invoked", dto);

		if	   ( dto.getStatus() == null && dto.getSearchText() == null ) {
			//검색 리스트: 활성화상태(true) 
			log.debug("\t+ 검색 리스트: 활성화상태(true) ");
			return this.dao.findByEnabled(true, paging);
		}
		else if( dto.getStatus() != null && dto.getSearchText() == null ) {
			//검색 리스트: 활성화상태(true) + status
			log.debug("\t+ 검색 리스트: 활성화상태(true) + status ");
			return this.dao.findByEnabledAndStatus(true, dto.getStatus(), paging);
		}
		else if( dto.getStatus() == null && dto.getSearchText() != null ) {
			log.debug("\t+ 검색 리스트: 3 ");
			return switch (dto.getSearchWord()) {
			            case "name" 	-> this.dao.findByEnabledAndNameContaining(true, dto.getSearchText(), paging);
			            case "detail" 	-> this.dao.findByEnabledAndDetailContaining(true, dto.getSearchText(), paging);
			            default			-> throw new IllegalArgumentException("swich_1 - Invalid search word: " + dto.getSearchWord());
					};
		}
		else if( dto.getStatus() != null && dto.getSearchText() != null ) {
			log.debug("\t+ 검색 리스트: 4 "+dto.getSearchWord()+" / "+dto.getSearchText());
			return switch (dto.getSearchWord()) {
			            case "name" 	-> {
			            	log.info("test:{}", dto.getSearchWord());
			            	yield this.dao.findByEnabledAndStatusAndNameContaining(true, dto.getStatus(), dto.getSearchText(), paging);
			            }
			            case "detail" 	-> this.dao.findByEnabledAndStatusAndDetailContaining(true, dto.getStatus(), dto.getSearchText(), paging);
			            default			-> throw new IllegalArgumentException("swich_2 - Invalid search word: " + dto.getSearchWord());
					};
		}
		
		return null;		
	} // getSearchList


	@Override
	public Page<Project> getUpCommingList(Pageable paging) {
		log.debug("\t+ ProjectServiceImpl -- getUpCommingList(()) invoked");
		
		return this.dao.findByEnabled(true, paging);
	} // getUpCommingList
	
	@Override
	public Project create(ProjectDTO dto) {	//등록 처리
		log.debug("\t+ ProjectServiceImpl -- create({}) invoked", dto);
		
		Project data = new Project();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Project getById(Long id) {	// 단일 조회
		log.debug("\t+ ProjectServiceImpl -- getById({}) invoked", id);
		
		Optional<Project> optionalProject = this.dao.findByEnabledAndId(true, id);
		Project project = optionalProject.get();
		
		return project;
	} // getById
	
	@Override
	public Boolean update(ProjectDTO dto) {//수정 처리
		log.debug("\t+ ProjectServiceImpl -- update({}) invoked", dto);
		
//		Project data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public String deleteById(Long id) { // 삭제 처리
		log.debug("\t+ ProjectServiceImpl -- deleteById({}) invoked", id);
		
		Optional<Project> optionalProject = this.dao.findByEnabledAndId(true, id);
		
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
			project.setEnabled(false);
			
			Project delete = this.dao.save(project);
			
			return "Delete success";
		}//if

		return "Delete fail";
		
		
	}//deleteById
	
	
	
}//end class
