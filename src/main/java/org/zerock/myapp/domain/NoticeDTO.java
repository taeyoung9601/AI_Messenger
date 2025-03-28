package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;


/**
 * 게시판 - 공지사항 DTO
 */

@Data
public class NoticeDTO {
	private Long id;

	
	
	private Boolean enabled;					//활성화상태(1=유효,0=삭제)
	
	private Date crtDate;						//등록일
	private Date udtDate;						//수정일
	
	
	private String searchWord;
	private String searchText;
	
} // end class
