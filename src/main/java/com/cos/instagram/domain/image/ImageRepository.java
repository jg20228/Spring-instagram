package com.cos.instagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	//@Query(value = "SELECT COUNT(*) FROM image WHERE userId = 1?", nativeQuery = true) ->나중에 팔로우할때
	List<Image> findByUserId(int userId);
	
	// 내가 팔로우 하지 않은 사람들의 이미지들(최대 20개)
	@Query(value = "select * from image where userId in (select id from user where id != ?1 and id not in (select toUserId from follow where fromUserId = ?1)) limit 20", nativeQuery = true)
	List<Image> mNonFollowImage(int loginUserId);
	
	@Query(value="select * from image where userId in (select toUserId from follow where fromUserId = ?1)", nativeQuery = true)
	List<Image> mFeeds(int loginUserId);
}
