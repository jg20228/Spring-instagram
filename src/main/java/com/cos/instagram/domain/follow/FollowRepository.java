package com.cos.instagram.domain.follow;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer>{
	//내가 만드는 함수는 m을 붙이고
	
	@Query(value = "SELECT count(*) FROM follow WHERE toUserId = ?1",nativeQuery=true)
	int mCountByFollower(int userId);

	@Query(value = "SELECT count(*) FROM follow WHERE fromUserId = ?1",nativeQuery=true)
	int mCountByFollowing(int userId);
	
	@Query(value = "SELECT count(*) FROM follow where fromUserId = ?1 and toUserId= ?2",nativeQuery = true)
	int mfollowState(int loginId, int pageUserId);
	
	//삭제시에는 @Transactional (javax 임포트)
	//수정시에는 @Modifying 필요
	//mDeleteFollow()
	
	@Modifying //Modifying 안에 Transactional 들어있음
	@Query(value = "DELETE FROM follow where  fromUserId = ?1 and toUserId = ?2",nativeQuery = true)
	int mDeleteFollow(int loginId, int pageUserId);
	
	//@Transactional
	//int deleteByFromUserIdAndToUserId();
	
	//mSaveFollow()
	@Modifying
	@Query(value = "INSERT INTO follow(createDate, fromUserId, toUserId) VALUES(now(), ?1, ?2)",nativeQuery = true)
	int mSaveFollow(int loginId, int pageUserId);
}
