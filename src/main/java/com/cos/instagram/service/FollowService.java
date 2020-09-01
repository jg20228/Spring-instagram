package com.cos.instagram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.domain.follow.FollowRepository;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
	
	private final FollowRepository followRepository;

	
	////////
	@Transactional
	public void 팔로우(int id, LoginUser loginUser) {
		int result = followRepository.mSaveFollow(loginUser.getId(), id);
		System.out.println("팔로우 result :"+result);
	}
	
	@Transactional
	public void 팔로우취소(int id, LoginUser loginUser) {
		int result =  followRepository.mDeleteFollow(loginUser.getId(), id);
		System.out.println("팔로우취소 result :"+result);
	}
}
