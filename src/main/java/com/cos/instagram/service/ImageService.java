package com.cos.instagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.tag.Tag;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.util.Utils;
import com.cos.instagram.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final UserRepository userRepository;
	
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public List<Image> feed(int userId){
		List<Image> images = imageRepository.mFeed(userId);
		return images;
	}
	
	@Transactional
	public void 사진업로드(ImageReqDto imageReqDto, int userId) {
		
		User userEntity = userRepository.findById(userId)
				.orElseThrow(null);
		
		UUID uuid = UUID.randomUUID();
		
		String imageFileName = uuid+""+imageReqDto.getFile().getOriginalFilename();
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try {
			//하드에 Input
			Files.write(imageFilePath, imageReqDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//1. Image 저장
		Image image = imageReqDto.toEntity(imageFileName, userEntity);
		Image imageEntity = imageRepository.save(image);
		
		//2. Tag 저장
		
		List<String> tagNames = Utils.tapParse(imageReqDto.getTags());
		for (String name : tagNames) {
			Tag tag = Tag.builder()
					.image(imageEntity)
					.name(name)
					.build();
			tagRepository.save(tag);
		}
	}
}
