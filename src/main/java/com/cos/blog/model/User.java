package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert //insert시에 null인 필드를 제외시켜준다.
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다. 
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(
        name = "UK_USER", columnNames = {"username"}
)})
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;  //시퀀스, auto_increment
	
	@Column(nullable = false, length=100, unique = true)
	private String username; //아이디
	
	@Column(nullable = false, length=100)
	private String password; 
	
	@Column(nullable = false, length=50)
	private String email;
	
	//@ColumnDefault("'user'") //문자라는것을 알려줘야 한다.
	//DB는 RoleType이라는게 없음
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. // ADMIN, USER
	
	@Column(length = 50)
	private String oauth; //kakao, google
	
	@CreationTimestamp //INSERT시 시간이 자동으로 입력
	private Timestamp createDate;
	//private Timestamp updateDate;
	
}
