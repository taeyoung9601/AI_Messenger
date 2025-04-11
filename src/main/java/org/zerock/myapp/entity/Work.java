package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.domain.WorkDTO;
import org.zerock.myapp.util.BooleanToIntegerConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Builder
@Data

// 업무 entity

@Entity
@Table(name="T_WORK")
public class Work implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	//1. pk
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique=true, nullable=false)
	private Long id; // 업무 id
	
	@Column(nullable=false)
	private String name; // 업무명

	@Column(nullable=true)
	private String detail; // 업무내용
	
	@Column(nullable=true)
	private String memo; // 업무 메모

	@Column(nullable=false)
	private Integer status; // 업무상태(진행예정=1, 진행중=2, 완료대기=3, 완료=4)

	@Column(nullable=false)
	private Integer type; // 업무분류(개발=1, 운영=2, 인사=3, 회계=4, 마케팅=5)

	@Column(nullable=false)
	private String startDate; // 시작일
	
	@Column(nullable=false)
	private String endDate; // 종료일
	
	@Convert(converter = BooleanToIntegerConverter.class)
	@Column(nullable=false)
	private Boolean enabled = true; // 활성화상태(1=유효,0=삭제)
	
	
	@CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
	@Column(name="CRT_DATE", nullable = false)
	private Date crtDate; // 생성일

	@CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
	@Column(name="UDT_DATE")
	private Date udtDate; // 수정일

	
	// join
	@ManyToOne
	@JoinColumn(name="EMPNO")
	private Employee Employee; // 지시자 id

	@ToString.Exclude
	@OneToMany(mappedBy="Work")
	private List<WorkEmployee> WorkEmployees = new Vector<>(); // 업무-사원 테이블


	public WorkEmployee addWorkEmployee(WorkEmployee WorkEmployee) {
		getWorkEmployees().add(WorkEmployee);
		WorkEmployee.setWork(this);

		return WorkEmployee;
	} // addWorkEmployee

	public WorkEmployee removeWorkEmployee(WorkEmployee WorkEmployee) {
		getWorkEmployees().remove(WorkEmployee);
		WorkEmployee.setWork(null);

		return WorkEmployee;
	} // removeWorkEmployee
	
	
	public static Work toEntity(WorkDTO dto) { // DTO -> 엔티티 편의메소드
        return Work.builder()
                .id(dto.getId())
                .name(dto.getName())
                .detail(dto.getDetail())
                .memo(dto.getMemo())
                .status(dto.getStatus())
                .type(dto.getType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .enabled(dto.getEnabled())
                .build();
    } // toEntity

} // end class