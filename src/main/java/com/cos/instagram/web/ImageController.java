package com.cos.instagram.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.instagram.config.auth.PrincipalDetails;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.domain.user.User;

@Controller
public class ImageController {

	@GetMapping({"","/","/image/feed"})
	public String feed(@AuthenticationPrincipal PrincipalDetails principal, HttpSession session) {
		//메인페이지가 될것임
		System.out.println("ImageController @AuthenticationPrincipal: "+principal.getUser());
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser"); //나도 session.getAttribute를 계속하기 힘들어서 @loginUser을 만들예정
		System.out.println("loginUser : "+loginUser);
		return "image/feed";
	}
}
