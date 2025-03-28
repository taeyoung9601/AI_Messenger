package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * 게시판 - 공지사항 Entity
 */

@Data

//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
//	"crtDate",
	"udtDate"
})

@Entity
@Table(name = "T_NOTICE")
public class Notice implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id;
	
	
} // end class
