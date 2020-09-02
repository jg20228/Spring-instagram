package com.cos.instagram.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instagram.config.auth.Cos;
import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.service.ImageService;
import com.cos.instagram.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;

	@GetMapping({"","/","/image/feed"})
	public String feed(@LoginUserAnnotation LoginUser loginUser,@Cos String cosDto) {
		//메인페이지가 될것임
		System.out.println("loginUser : "+loginUser);
		System.out.println("cos : "+cosDto);
		return "image/feed";
	}
	
	
	//세션이 있어서 id값 안 받아도 됨
	@GetMapping("/image/uploadForm")
	public String imageUploadForm() {
		return "image/image-upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(
			@LoginUserAnnotation LoginUser loginUser,
			ImageReqDto imageReqDto) {
		System.out.println(imageReqDto);
		imageService.사진업로드(imageReqDto, loginUser.getId());
		return "redirect:/";
	}
	
	@GetMapping("/image/explore")
	public String imageExplore(@LoginUserAnnotation LoginUser loginUser, Model model) {
		List<Image> images = imageService.feed(loginUser.getId());
		model.addAttribute("dto", images);
		return "image/explore";
	}
}
