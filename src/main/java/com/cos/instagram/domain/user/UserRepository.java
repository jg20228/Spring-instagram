package com.cos.instagram.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//JpaRepository가 extends 되면 @Repository 필요 없음. IoC 자동으로 됨
//기본적인 CRUD를 가지고 있음
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	
	@Query(value = "update user set bio = '', gender= '', name = '', phone = '', profileImage = '', website=''\r\n" + 
			" WHERE id = 3",nativeQuery = true)
	User updateByUser(User user);
}
