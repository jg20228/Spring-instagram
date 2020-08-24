package com.cos.instagram.domain.like;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//한명의 유저가 여러 곳에 좋아요를 할 수 있음
	@ManyToOne//EAGER
	@JoinColumn(name="userId") //컬럼이름 정하기
	private User user;
	@ManyToOne
	@JoinColumn(name="imageId") //컬럼이름 정하기
	private Image image;
	
	
	@CreationTimestamp
	private Timestamp createDate;
}
