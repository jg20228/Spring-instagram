package com.cos.instagram.domain.user;

import java.util.List;

import com.cos.instagram.domain.image.Image;

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
	
	private User userEntity;
	private List<Image> imageEntity;
	
	private int imageConut;
	private int followerCount;
	private int followingCount;
	
}
