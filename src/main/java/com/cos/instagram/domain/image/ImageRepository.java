package com.cos.instagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	//@Query(value = "SELECT COUNT(*) FROM image WHERE userId = 1?", nativeQuery = true) ->나중에 팔로우할때
	List<Image> findByUserId(int userId);
}
