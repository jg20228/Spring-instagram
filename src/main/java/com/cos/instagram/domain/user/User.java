package com.cos.instagram.domain.user;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String name;
	private String website;
	private String bio; //자기소개
	private String phone;
	private String gender;
	private String profileImage;
	@Enumerated(EnumType.STRING)
	private UserRole role;//강제성이 부여됨 + 테이블에서 스트링이 아니니까 적어줘야할게 있다
	private String provider;
	private String providerId;
	@CreationTimestamp //Insert 될때 현재시간이 들어감
	private Timestamp createDate;

}
