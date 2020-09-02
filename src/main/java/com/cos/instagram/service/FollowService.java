package com.cos.instagram.service;

import java.util.List;
<<<<<<< HEAD
import java.util.function.Supplier;
=======
>>>>>>> 2ee98dd6607ae347ca76373fe5ad6bcf045d59ea

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instagram.domain.follow.FollowRepository;
<<<<<<< HEAD
import com.cos.instagram.domain.follow.FollowingListRespDto;
=======
import com.cos.instagram.domain.follow.FollowRespDto;
>>>>>>> 2ee98dd6607ae347ca76373fe5ad6bcf045d59ea
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserProfileImageRespDto;
import com.cos.instagram.domain.user.UserProfileRespDto;
import com.cos.instagram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
	
<<<<<<< HEAD
	@PersistenceContext
	private EntityManager em; 
	//내부적으로 돌아가는 JPA 함수
	//entitiy로 mapping 해주는애들이다. (하이버 네이트 기술)
	
=======
	
	@PersistenceContext
	private EntityManager em;
>>>>>>> 2ee98dd6607ae347ca76373fe5ad6bcf045d59ea
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
	
	@Transactional(readOnly = true)
	public List<FollowingListRespDto> 팔로윙리스트(int pageId, int loginUserId) {
		System.out.println("1");
		
		//1.이미지 카운트
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  u.id, u.name, u.username, ");
		sb.append("(select true ");
		sb.append("from follow f2 ");
		sb.append("where f1.fromUserId = f2.toUserId ");
		sb.append("and f1.toUserId = f2.fromUserId) as followState ");
		sb.append("FROM user u INNER JOIN follow f1 ");
		sb.append("ON u.id = f1.toUserId ");
		sb.append("AND fromUserId = ? ");
		
		// 쿼리문 ? 대신에 변수를 넣으면 인젝션 공격을 받을 수 있음  
		System.out.println("2");

		String q = sb.toString();
		Query query = em.createNativeQuery(q, "FollowingListRespDtoMapping")
				.setParameter(1, loginUserId);//첫번째 물음표에 id값넣기, 
		System.out.println("1");
		List<FollowingListRespDto> followingEntity = query.getResultList(); //@ConstructorResult를 잘만들어야함
		return followingEntity;
	}
}
