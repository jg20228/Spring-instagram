package com.cos.instagram.service;


import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instagram.domain.follow.FollowRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.web.dto.JoinReqDto;
import com.cos.instagram.web.dto.UserProfileImageRespDto;
import com.cos.instagram.web.dto.UserProfileRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final로 DI
@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@PersistenceContext
	private EntityManager em; 
	//내부적으로 돌아가는 JPA 함수
	//entitiy로 mapping 해주는애들이다. (하이버 네이트 기술)
	
	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${file.path}")
	private String uploadFolder;

	@Transactional
	public void 프로필사진업로드(LoginUser loginUser, MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String imageFilename = 
				uuid+"_"+file.getOriginalFilename();
		Path imageFilepath = Paths.get(uploadFolder+imageFilename);
		try {
			Files.write(imageFilepath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		User userEntity = userRepository.findById(loginUser.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {
			@Override
			public MyUserIdNotFoundException get() {
				return new MyUserIdNotFoundException();
			}
		});

		// 더티체킹
		userEntity.setProfileImage(imageFilename);
	}

	@Transactional
	public void 회원수정(User user) {
		// 더티 체킹
		User userEntity = userRepository.findById(user.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {
			@Override
			public MyUserIdNotFoundException get() {
				return new MyUserIdNotFoundException();
			}
		});
		userEntity.setName(user.getName());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setBio(user.getBio());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
	}

	@Transactional(readOnly = true)
	public User 회원정보(LoginUser loginUser) {
		return userRepository.findById(loginUser.getId())
				.orElseThrow(new Supplier<MyUserIdNotFoundException>() {
					@Override
					public MyUserIdNotFoundException get() {
						return new MyUserIdNotFoundException();
					}
				});
	}
	
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
		boolean followState;
		
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
		StringBuilder sb = new StringBuilder();
		sb.append("select im.id, im.imageUrl, ");
		sb.append("(select count(*) from likes lk where lk.imageId = im.id) as likeCount, "); //좋아요 수
		sb.append("(select count(*) from comment ct where ct.imageId = im.id) as commentCount ");//댓글 수
		sb.append(" from image im where im.userId = ? ");//댓글 수
		
		// 쿼리문 ? 대신에 변수를 넣으면 인젝션 공격을 받을 수 있음  
		
		String q = sb.toString();
		Query query = em.createNativeQuery(q, "UserProfileImageRespDtoMapping").setParameter(1, id); //첫번째 물음표에 id값넣기, 
		List<UserProfileImageRespDto> imagesEntity = query.getResultList(); //@ConstructorResult를 잘만들어야함
		//위에껀 복붙함
		
		//em.persist(imagesEntity);//영속화
		//em.detach(imagesEntity);//영속화를 해제 (셀렉트할때마다 DB에서 가져옴)
		
		
		//List<Image> imagesEntity = imageRepository.findByUserId(id);
		imageConut = imagesEntity.size();
		
		//2.팔로우 수 (수정해야됨)
		followerCount = followRepository.mCountByFollower(id);
		followingCount = followRepository.mCountByFollowing(id);

		//3.팔로우 유무
		followState = followRepository.mfollowState(loginUser.getId(), id) == 1 ? true : false ;
		
		//4.최종 마무리
		//UserProfileRespDto는 Entitiy가 아니라서 무한참조 현상이 발생하지 않음. 원하는 결과만 return 가능.
		UserProfileRespDto userProfileRespDto = 
				UserProfileRespDto.builder()
				.followState(followState)
				.pageHost(id==loginUser.getId())
				.followerCount(followerCount)
				.followingCount(followingCount)
				.imageConut(imageConut)
				.userEntity(userEntity)
				.imageEntity(imagesEntity)//수정완료(댓글수,좋아요수)	+ 여기 안에 댓글수랑 좋아요수 넣는게 어려움!
				.build();
		return userProfileRespDto;
	}
}
