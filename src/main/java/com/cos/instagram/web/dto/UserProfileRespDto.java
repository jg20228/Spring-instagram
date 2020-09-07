package com.cos.instagram.web.dto;

import java.util.List;

import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRespDto {
	
	private boolean pageHost; //페이지의 주인 확인
	private boolean followState; //true(팔로우 취소), false(팔로우)
	
	private User userEntity;
	private List<UserProfileImageRespDto> imageEntity;
	
	private int imageConut;
	private int followerCount;
	private int followingCount;
}
