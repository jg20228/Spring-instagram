package com.cos.instagram.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instagram.service.UserService;
import com.cos.instagram.web.dto.JoinReqDto;

import lombok.RequiredArgsConstructor;


//인증 안된 사람이 들어 올 수 있는 컨트롤러를 따로 만들어줘야한다.
@RequiredArgsConstructor
@Controller
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	private final UserService userService;
	
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		log.info("/auth/loginForm 진입");
		return "auth/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		log.info("/auth/joinForm 진입");
		return "auth/joinForm";
	}
	
	@PostMapping("/auth/join")
	public String join(JoinReqDto joinReqDto) {//Validate 걸면 BindResult까지 걸음
		//validate로 Null값 체크할때 user로 받으면 null값이 많아서 안됨
		//그래서 requestDto를 만듬 -> Join Req Dto (함수명, Req or Resp, Dto)
		//Form 전송은 스프링이 원래 파싱함
		log.info(joinReqDto.toString());
		//서비스로 넘어가기전 validate 호출
		
		//서비스 호출 User
		userService.회원가입(joinReqDto);
		
		return "redirect:/auth/loginForm";//사진나오는 페이지로 이동
	}
}
