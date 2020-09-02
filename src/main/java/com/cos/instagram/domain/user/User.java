package com.cos.instagram.domain.user;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instagram.domain.follow.FollowingListRespDto;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.web.dto.JoinReqDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@SqlResultSetMapping(
		name = "FollowingListRespDtoMapping", 
		classes = @ConstructorResult(
				targetClass = FollowingListRespDto.class, 
				columns = {
		// 통계같은것 짤때 씀
		@ColumnResult(name = "id", type = Integer.class), 
		@ColumnResult(name = "name", type = String.class),
		@ColumnResult(name = "username", type = String.class), 
		@ColumnResult(name = "followState", type = String.class) 
		}
	)
)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String username;
	private String password;
	private String email; // 모델 만들때 실수로 안넣음.
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
	
	@JsonIgnoreProperties("user") //유저라는 프로퍼티는 무시
	@OneToMany(mappedBy = "user") //User를 셀렉트했을때만 사용함!
	private List<Image> images;
}
