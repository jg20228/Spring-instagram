package com.cos.instagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.domain.comment.Comment;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.like.Likes;
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

	@Transactional(readOnly = true)
	public List<Image> 피드사진(int loginUserId, String tag){
		List<Image> images = null;
		if(tag == null || tag.equals("")) {
			images = imageRepository.mFeeds(loginUserId);
		}else {
			images = imageRepository.mFeeds(tag);
		}
		
		for (Image image : images) {
			image.setLikeCount(image.getLikes().size());
			for (Likes like : image.getLikes()) {
				if(like.getUser().getId()==loginUserId) {
					image.setLikeState(true);
					
				}
			}
			//댓글 주인 여부
			for (Comment comment : image.getComments()) {
				if(comment.getUser().getId() == loginUserId) {
					comment.setCommentHost(true);
				}
			}
		}
		return images;
	}
	
	
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public List<Image> feed(int loginUserId){
		List<Image> images = imageRepository.mFeeds(loginUserId);
		for (Image image : images) {
			image.setLikeCount(image.getLikes().size());
			for (Likes like : image.getLikes()) {
				if(like.getUser().getId() == loginUserId) {
					image.setLikeState(true);
				}
			}
		}
		return images;
	}
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(int loginUserId) {
		return imageRepository.mNonFollowImage(loginUserId);
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
