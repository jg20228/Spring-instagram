package com.cos.instagram.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instagram.domain.follow.Follow;
import com.cos.instagram.domain.follow.FollowRepository;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.like.Likes;
import com.cos.instagram.domain.like.LikesRepository;
import com.cos.instagram.domain.tag.Tag;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.domain.user.UserRole;

@RestController
public class TestApiController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private FollowRepository followRepository; 
	
	@Autowired
	private LikesRepository likesRepository; 
	
	//회원가입 임시로 하는 컨트롤러
	@PostMapping("/test/api/join")
	public User join(@RequestBody User user) {
		user.setRole(UserRole.USER); //USER getKey하면 값이 나옴
		User userEntity = userRepository.save(user);
		return userEntity;
	}
	
	//팔로우 임시로 입력하는 컨트롤러
	@PostMapping("/test/api/follow/{fromUserId}/{toUserId}")
	public String follow(@PathVariable int fromUserId, @PathVariable int toUserId) {
		
		User fromUserEntity = userRepository.findById(fromUserId).get();
		User toUserEntity = userRepository.findById(toUserId).get();
		
		Follow follow = Follow.builder()
				.fromUser(fromUserEntity)
				.toUser(toUserEntity)
				.build();
		followRepository.save(follow);
		//http://localhost:8080/test/api/follow/1/2
		return fromUserEntity.getUsername()+"이"+toUserEntity.getUsername()+"을 팔로우 하였습니다.";
	}
	
	//좋아요 테스트를 위한 입력 컨트롤러
	@PostMapping("/test/api/like/{imageId}/{userId}")
	public String likes(@PathVariable int imageId, @PathVariable int userId) {
		
		Image imageEntitiy = imageRepository.findById(imageId).get();
		User userEntitiy = userRepository.findById(userId).get();
		
		Likes likes = Likes.builder()
				.user(userEntitiy)
				.image(imageEntitiy)
				.build();
		likesRepository.save(likes);
		//http://localhost:8080/test/api/like/1/2
		return likes.getUser().getUsername()+" 님이 "+likes.getImage().getCaption()+"에 좋아요를 눌렀습니다.";
	}
	
	
	//이미지 입력 컨트롤러
	@PostMapping("/test/api/image/{caption}")
	public String image(@PathVariable String caption) {
		User userEntitiy = userRepository.findById(1).get();
		System.out.println(caption);
		//이미지와 태그의 모순 - 이미지를 넣고나서 Tag를 insert해야 한다.
		Image image = Image.builder()
				.location("외국")
				.caption(caption)
				.user(userEntitiy)
				.build();
		//save하고나서 이것을 Tag에 넣어야함
		Image imageEntity = imageRepository.save(image);
		
		List<Tag> tags = new ArrayList<>();
		Tag tag1 = Tag.builder()
				.name("#외국")
				.image(imageEntity)//image가 아직 생성안되서 id가 없어서 안됨
				.build();
		Tag tag2 = Tag.builder()
				.name("#여행")
				.image(imageEntity)
				.build();
		tags.add(tag1);
		tags.add(tag2);
		
		//save(x) -> saveAll(o)
		tagRepository.saveAll(tags);
		
		// MessageConverter의 Jackson 내부적으로 get을 계속 호출해서 값을 들고와서 JSON으로 바꿔서 return함
		return "Image Insert 잘됨"; 
	}
	
	@GetMapping("/test/api/image/list")
	public List<Image> imageList(){
		return imageRepository.findAll();
	}
	
	@GetMapping("/test/api/tag/list")
	public List<Tag> tagList(){
		return tagRepository.findAll();
	}
	
	
	
}
