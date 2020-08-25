package com.cos.instagram.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.instagram.domain.user.User;


public class PrincipalDetails implements UserDetails{

	private static final Logger log = LoggerFactory.getLogger(PrincipalDetails.class);
	private static final long serialVersionUID = 1L; //?
	private User user;
	
	
	public PrincipalDetails(User user) {
		super();
		log.debug("PrincipalDetails 생성자 진입");
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add( () -> (user.getRole().getKey()));//getKey 기억하기
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	} 

}
