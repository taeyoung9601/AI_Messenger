package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.FileDTO;
import org.zerock.myapp.entity.File;
import org.zerock.myapp.persistence.FileRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class FileServiceImpl implements FileService {
    @Autowired FileRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("FileServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<File> getAllList() {	//검색 없는 전체 리스트
		log.debug("FileServiceImpl -- getAllList() invoked");
		
		List<File> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<File> getSearchList(FileDTO dto) {	//검색 있는 전체 리스트
		log.debug("FileServiceImpl -- getSearchList(()) invoked", dto);

		List<File> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public File create(FileDTO dto) {	//등록 처리
		log.debug("FileServiceImpl -- create({}) invoked", dto);
		
		File data = new File();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public File getById(String id) {	// 단일 조회
		log.debug("FileServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		File data = new File();//dao.findById(id).orElse(new File());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(FileDTO dto) {//수정 처리
		log.debug("FileServiceImpl -- update({}) invoked", dto);
		
//		File data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("FileServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
