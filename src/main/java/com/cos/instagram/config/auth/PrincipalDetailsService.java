package com.cos.instagram.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

	
	private static final Logger log = LoggerFactory.getLogger(PrincipalDetailsService.class);
	private final UserRepository userRepository;
	
	//Security Session > Authentication > UserDetails
	//정상적으로 리턴 되면 @AuthenticationPrincipal을 사용 가능함.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("UserDetailsService loadUserByUsername 진입");
		//여기서 머스태치를 쓸려면 세션을 추가로 저장하면된다.
		User userEntity = 
				userRepository.findByUsername(username).get();
		return new PrincipalDetails(userEntity);
	}
}
