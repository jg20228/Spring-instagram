package com.cos.instagram.web.dto;

import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRole;
import com.cos.instagram.web.dto.LoginReqDto.LoginReqDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinReqDto {
	
	private String email;
	private String name;
	private String username;
	private String password;
	
	
	//잔기술?
	public User toEntity() {
		//dto에 있는걸 하나씩 값을 넣기 귀찮으니까
		return User.builder()
				.email(email)
				.name(name)
				.username(username)
				.password(password)
				.role(UserRole.USER)
				.build();
	}
}
