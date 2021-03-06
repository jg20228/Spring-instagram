package com.cos.instagram.web;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.service.FollowService;
import com.cos.instagram.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FollowController {
	
	private final FollowService followService;
	
	@PostMapping("/follows/{id}")
	public ResponseEntity<?> insertFollow(@PathVariable int id,@LoginUserAnnotation LoginUser loginUser) {
		followService.팔로우(id, loginUser);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
		//return 할때는 스트링이면 스트링 JSON이면 JSON 통일 하는게 좋다.
	}
	
	@DeleteMapping("/follows/{id}")
	public ResponseEntity<?> deleteFollow(@PathVariable int id,@LoginUserAnnotation LoginUser loginUser) {
		followService.팔로우취소(id, loginUser);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	@GetMapping("/follow/followingList/{toUserId}")
	public String followingList(@PathVariable int toUserId,@LoginUserAnnotation LoginUser loginUser,Model model) {
		model.addAttribute("dto", followService.팔로잉리스트(loginUser.getId(), toUserId));
		return "follow/following-list";
	}
	
	@GetMapping("/follow/followerList/{toUserId}")
	public String followerList(@PathVariable int toUserId,@LoginUserAnnotation LoginUser loginUser,Model model) {
		model.addAttribute("dto", followService.팔로워리스트(loginUser.getId(), toUserId));
		return "follow/follower-list";
	}
}
