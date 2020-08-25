package com.cos.instagram.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.web.dto.JoinReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final로 DI
@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional//transactional의 타이밍, open in veiw를 생각하면 언제까지 유지되는지
	public void 회원가입(JoinReqDto joinReqDto) {
		String encPassword = 
				bCryptPasswordEncoder.encode(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword);
		userRepository.save(joinReqDto.toEntity());
	}
}
