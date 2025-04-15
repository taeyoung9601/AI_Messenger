package org.zerock.myapp.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.File;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;


/**
 * 메시지 DTO
 */

@Data
public class MessageDTO {
	private Long id; // 메시지 id

	private String detail; // 내용
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	@JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화 시 필요
	@JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화 시 필요
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss") // 원하는 형태의 포멧
	private LocalDateTime crtDate; // 생성일
	private LocalDateTime udtDate; // 수정일

	
	// join
	private List<File> Files = new Vector<>(); // 파일
	private Chat Chat; // 채팅
	private Long chatId;
	private String empno;
	private Employee Employee; // 사원
	
	private String searchWord; // 검색항목
	private String searchText; // 검색문
	
} // end class
