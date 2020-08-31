package com.cos.instagram.service;


import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserProfileRespDto;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.web.dto.JoinReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final로 DI
@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional//transactional의 타이밍, open in veiw를 생각하면 언제까지 유지되는지
	public void 회원가입(JoinReqDto joinReqDto) {
		String encPassword = 
				bCryptPasswordEncoder.encode(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword);
		userRepository.save(joinReqDto.toEntity());
	}
	
	//읽기 전용 트랜잭션
	//(1) 변경 감지 연산을 하지 않음. 
	//== JPA는 영속성 컨텍스트가 있는데 DB랑 계속 비교를 하는데 더티 체킹? (쓸데 없는 연산 감소)
	//이걸 걸어두면 변경감지 연산을 안함 

	//(2) isolation(고립성)을 위해. Phantom read 문제가 일어나지않음 (=팬톰 현상이 일어나지 않음)
	
	@Transactional(readOnly = true)
	public UserProfileRespDto 회원프로필(int id, LoginUser loginUser) {
		int imageConut;
		int followerCount;
		int followingCount;
		
		//다른 유저가 올 수 있어서
		User userEntity = userRepository.findById(id)
				.orElseThrow(new Supplier<MyUserIdNotFoundException>() {
					@Override
					public MyUserIdNotFoundException get() {
						//이것을 낚아챌 global exception을 만든다.
						return new MyUserIdNotFoundException();
						//return new MyUserIdNotFoundException("해당 id의 유저가 없습니다.");
					}
				});
		//1.이미지 카운트
		List<Image> imagesEntity = imageRepository.findByUserId(id);
		imageConut = imagesEntity.size();
		
		//2.팔로우 수 (수정해야됨)
		followerCount = 50;
		followingCount = 100;
		
		//3.최종 마무리
		//userEntity.setFollowerCount(followerCount);
		//userEntity.setFollowingCount(followingCount);
		//userEntity.setImageConut(imageConut);
		
		UserProfileRespDto userProfileRespDto = 
				UserProfileRespDto.builder()
				.pageHost(id==loginUser.getId())
				.followerCount(followerCount)
				.followingCount(followingCount)
				.imageConut(imageConut)
				.userEntity(userEntity)
				.imageEntity(imagesEntity)//수정필요(댓글수,좋아요수)	+ 여기 안에 댓글수랑 좋아요수 넣는게 어려움!
				.build();
		
		return userProfileRespDto;
	}
}
