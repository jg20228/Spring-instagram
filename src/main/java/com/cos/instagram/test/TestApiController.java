package com.cos.instagram.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
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
	
	@GetMapping("/test/api/join")
	public User user() {
		User user = User.builder()
				.name("cos")
				.password("1234")
				.phone("0102222")
				.bio("안녕 난 코스야")
				.role(UserRole.USER)
				.build();
		User userEntity = userRepository.save(user);
		return userEntity;
	}
	
	@GetMapping("/test/api/image")
	public String image() {
		User userEntitiy = userRepository.findById(1).get();
		//이미지와 태그의 모순 - 이미지를 넣고나서 Tag를 insert해야 한다.
		Image image = Image.builder()
				.location("다낭")
				.caption("설명")
				.user(userEntitiy)
				.build();
		//save하고나서 이것을 Tag에 넣어야함
		Image imageEntity = imageRepository.save(image);
		
		List<Tag> tags = new ArrayList<>();
		Tag tag1 = Tag.builder()
				.name("#다낭")
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
