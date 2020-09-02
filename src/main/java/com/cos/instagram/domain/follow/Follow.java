package com.cos.instagram.domain.follow;

import java.sql.Timestamp;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserProfileImageRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//한명의 User는 팔로워를 여러명에게 할 수 있음
	@ManyToOne
	@JoinColumn(name="fromUserId") //컬럼이름 정하기
	private User fromUser;
	
	//한명의 User는 팔로워를 여러번 당할 수 있음
	@ManyToOne
	@JoinColumn(name="toUserId") //컬럼이름 정하기
	private User toUser;
	//-----------ManyToMany는 여기까지만	
	
	//ManyToOne으로 하면 추가적인 컬럼 가능
	@CreationTimestamp
	private Timestamp createDate;
}
