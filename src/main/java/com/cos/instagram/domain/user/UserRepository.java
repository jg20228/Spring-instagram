package com.cos.instagram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository가 extends 되면 @Repository 필요 없음. IoC 자동으로 됨
//기본적인 CRUD를 가지고 있음
public interface UserRepository extends JpaRepository<User, Integer>{

}
