package com.cos.instagram.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.domain.user.User;
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
	public String profileEdit(@LoginUserAnnotation LoginUser loginUser, Model model) {
		Optional<User> user = userService.회원조회(loginUser.getId());
		model.addAttribute("user", user);//이부분
		return "user/profile-edit";
	}
	
	@PutMapping("/user/profileEidt")
	public String profileEditProc(@LoginUserAnnotation LoginUser loginUser, User user) {
		userService.회원수정(user);
		return "user/profile";
	}
}
