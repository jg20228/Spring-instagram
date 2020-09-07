package com.cos.instagram.web.dto;

import com.cos.instagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReqDto {
	private String username;
	private String password;
	
	//잔기술?
	public User toEntity() {
		//dto에 있는걸 하나씩 값을 넣기 귀찮으니까
		return User.builder()
				.username(username)
				.password(password)
				.build();
	}
}
