package org.zerock.myapp.service;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.entity.Member;
import org.zerock.myapp.persistence.MemberRepository;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired MemberRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("MemberServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Member> getAllList() {	//검색 없는 전체 리스트
		log.debug("MemberServiceImpl -- getAllList() invoked");
		
		List<Member> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Member> getSearchList(MemberDTO dto) {	//검색 있는 전체 리스트
		log.debug("MemberServiceImpl -- getSearchList(()) invoked", dto);

		List<Member> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Member create(MemberDTO dto) {	//등록 처리
		log.debug("MemberServiceImpl -- create({}) invoked", dto);
		
		Member data = new Member();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Member getById(String id) {	// 단일 조회
		log.debug("MemberServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Member data = new Member();//dao.findById(id).orElse(new Member());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(MemberDTO dto) {//수정 처리
		log.debug("MemberServiceImpl -- update({}) invoked", dto);
		
//		Member data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("MemberServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
	
	
}//end class
