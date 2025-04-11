package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data

// 채팅방-사원 PK 엔티티

@Embeddable
public class ChatEmployeePK implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	@Column(name="EMPNO")
	private String empno;

	@Column(name="CHAT_ID")
	private Long chatId;

} // end class