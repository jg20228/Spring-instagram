package com.cos.instagram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.instagram.config.auth.Cos;
import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;

@Controller
public class ImageController {

	@GetMapping({"","/","/image/feed"})
	public String feed(@LoginUserAnnotation LoginUser loginUser,@Cos String cosDto) {
		//메인페이지가 될것임
		System.out.println("loginUser : "+loginUser);
		System.out.println("cos : "+cosDto);
		return "image/feed";
	}
}
