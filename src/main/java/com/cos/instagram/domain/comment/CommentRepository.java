package com.cos.instagram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	@Modifying
	@Query(value="INSERT INTO comment(createDate,userId,imageId,content) VALUES(now(),?1,?2,?3)",nativeQuery = true)
	int mSave(int userId, int imageId, String content);
}
