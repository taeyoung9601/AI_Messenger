package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Data

//JSON 으로 변환해서 보낼때, 제외 할 항목
@JsonIgnoreProperties({
	"udtDate"
})

// 코드 entity

@Entity
@Table(name="T_CODE")
public class Code implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CodePK id; // 코드 ID
	
	@Column(nullable=false)
	private String name; // 이름

	@Column(nullable=false)
	private String description; // 설명

	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)

	@Column(nullable = false)
	private Integer orderNum;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE", nullable = false)
	private Date crtDate;

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate;
	
} // end class