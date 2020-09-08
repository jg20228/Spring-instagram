package com.cos.instagram.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.handler.ex.MyImageIdNotFoundException;
import com.cos.instagram.domain.comment.CommentRepository;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.noti.NotiRepository;
import com.cos.instagram.domain.noti.NotiType;
import com.cos.instagram.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {	
	private final CommentRepository commentRepository;
	private final NotiRepository notiRepository;
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 댓글쓰기(CommentRespDto commentRespDto) {
		//commentRepository.save(commentRespDto); comment 객체 만들어야해서 비추천
		//Query문을 직접만드는게 좋을수있음 (취향)
		commentRepository.mSave(
				commentRespDto.getUserId(), 
				commentRespDto.getImageId(), 
				commentRespDto.getContent()
				);
		Image imageEntity = imageRepository.findById(commentRespDto.getImageId()).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		notiRepository.mSave(commentRespDto.getUserId(), imageEntity.getUser().getId(), NotiType.COMMENT.name());
	}
	
	@Transactional
	public void 댓글삭제(int commentId) {
		commentRepository.deleteById(commentId);
	}
}
