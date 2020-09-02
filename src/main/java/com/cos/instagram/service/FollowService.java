package com.cos.instagram.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.domain.follow.FollowRepository;
import com.cos.instagram.domain.follow.FollowRespDto;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
	
	
	@PersistenceContext
	private EntityManager em;
	private final FollowRepository followRepository;

	
	public List<FollowRespDto> 팔로잉리스트(int loginUserId, int pageUserId){
		
		StringBuilder sb = new StringBuilder();
		sb.append("select u.id,u.username,u.name,u.profileImage, ");
		sb.append("if(u.id = ?, true, false) equalUserState,");
		sb.append("if((select true from follow where fromUserId = ? and toUserId = u.id), true, false) as followState ");
		sb.append("from follow f inner join user u on f.toUserId = u.id ");
		sb.append("and f.fromUserId = ?");
		String q = sb.toString();
		
		Query query = em.createNativeQuery(q, "FollowRespDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);
		List<FollowRespDto> followListEntity = query.getResultList();
		return followListEntity;
	}
	
	public List<FollowRespDto> 팔로워리스트(int loginUserId, int pageUserId){
		// 첫번째 물음표 loginUserId, 두번째 물음표 pageUserId
		StringBuilder sb = new StringBuilder();
		sb.append("select u.id,u.username,u.name,u.profileImage, ");
		sb.append("if(u.id = ?, true, false) equalUserState,");
		sb.append("if((select true from follow where fromUserId = ? and toUserId = u.id), true, false) as followState ");
		sb.append("from follow f inner join user u on f.fromUserId = u.id ");
		sb.append("and f.toUserId = ?");
		String q = sb.toString();

		Query query = em.createNativeQuery(q, "FollowRespDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);
		List<FollowRespDto> followerListEntity = query.getResultList();
		return followerListEntity;
	}
	
	////////
	@Transactional
	public void 팔로우(int id, LoginUser loginUser) {
		int result = followRepository.mSaveFollow(loginUser.getId(), id);
		System.out.println("팔로우 result :"+result);
	}
	
	@Transactional
	public void 팔로우취소(int id, LoginUser loginUser) {
		int result =  followRepository.mDeleteFollow(loginUser.getId(), id);
		System.out.println("팔로우취소 result :"+result);
	}
}
