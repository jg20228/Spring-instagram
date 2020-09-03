package com.cos.instagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	//@Query(value = "SELECT COUNT(*) FROM image WHERE userId = 1?", nativeQuery = true) ->나중에 팔로우할때
	List<Image> findByUserId(int userId);
	

	@Query(value = "SELECT * FROM image i WHERE i.userId not in (SELECT f.toUserId FROM follow f WHERE f.fromUserId = ?1) AND i.userId != ?1 limit 20",nativeQuery=true)
	List<Image> mFeed(int userId);
	
	
	@Query(value = "select * from image where userId in (SELECT toUserId FROM follow where fromUserId = ?1)",nativeQuery = true)
	List<Image> mFeeds(int loginUserId);
}

