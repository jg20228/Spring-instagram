package com.cos.instagram.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;


	@GetMapping("/user/{id}")
	public String user(@PathVariable int id,@LoginUserAnnotation LoginUser loginUser, Model model) {
		model.addAttribute("dto", userService.회원프로필(id, loginUser));
		return "user/profile";
	}
	
	@GetMapping("/user/profileEdit")
	public String profileEdit(@LoginUserAnnotation LoginUser loginUser) {
		return "user/profile-edit";
	}
}
