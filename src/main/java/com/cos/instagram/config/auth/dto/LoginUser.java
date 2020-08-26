package com.cos.instagram.config.auth.dto;

import com.cos.instagram.domain.user.User;

import lombok.Data;

@Data //생성자로만 Set할 예정이었으나 Data로 바뀜
public class LoginUser {
	private int id;
	private String username;
	private String email;
	private String name;
	private String role;
	private String porvider;
	private String porviderId;
	
	public LoginUser(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.name = user.getName();
		this.role = user.getRole().getKey();
		this.porvider = user.getProvider();
		this.porviderId = user.getProviderId();
	}
}
