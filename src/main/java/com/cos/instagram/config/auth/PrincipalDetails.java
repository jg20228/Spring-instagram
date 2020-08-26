package com.cos.instagram.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.instagram.domain.user.User;

import lombok.Data;


@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final Logger log = LoggerFactory.getLogger(PrincipalDetails.class);
	private static final long serialVersionUID = 1L; //?
	private User user;
	private Map<String,Object> attributes;
	
	
	//일반 로그인 용 생성자 UserDetails
	public PrincipalDetails(User user) {
		log.debug("PrincipalDetails 생성자 진입 : UserDetails");
		this.user = user;
	}
	
	//OAuth 로그인 용 생성자 OAuth2User
	public PrincipalDetails(User user, Map<String,Object> attributes) {
		log.debug("PrincipalDetails 생성자 진입 : OAuth2User");
		this.user = user;
		this.attributes = attributes;
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

	@Override
	public Map<String, Object> getAttributes() {
		//페이스북, 구글, 값이 다 다르니까 Map으로 받음.
		//private Map<String,Object> attributes;
		return attributes;
	}

	@Override
	public String getName() {
		return user.getProviderId();
	} 

}
