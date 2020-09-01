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

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{id}")
	public String user(@PathVariable int id,@LoginUserAnnotation LoginUser loginUser, Model model) {
		model.addAttribute("dto", userService.회원프로필(id, loginUser));
		return "user/profile";
	}
	
	@PostMapping("/follows/{id}")
	public void insertFollow(@PathVariable int id,@LoginUserAnnotation LoginUser loginUser) {
		userService.팔로우(id, loginUser);
	}
	
	@DeleteMapping("/follows/{id}")
	public void deleteFollow(@PathVariable int id,@LoginUserAnnotation LoginUser loginUser) {
		userService.팔로우취소(id, loginUser);
	}
}
