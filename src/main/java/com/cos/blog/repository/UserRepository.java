package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
// 스프링 IoC에서 객체로 가지고 있나요? 자동으로 빈으로 등록이 됨
// 그래서 @Repository 어노테이션 선언을 생략할 수 있다.
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
	
	//JPA Naming 쿼리 전략
	// JAP에 의해 자동으로 select * from user where username = ?1 and password = ?2 인 쿼리가 자동으로 생성되어 동작함
	//User findByUsernameAndPassword(String username, String password);
	
	//@Query(value="select * from user where username = ?1 and password = ?",nativeQuery = true)
	//User login(String username, String password);
}
