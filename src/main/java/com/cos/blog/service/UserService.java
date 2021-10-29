package com.cos.blog.service;


import com.cos.blog.model.Board;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.batch.FileFinder;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


//스프링 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 IoC
@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Transactional
	public User find(String username) {		
		User user = userRepository.findByUsername(username)
				.orElseGet(()->{
					return null;
				});				
		return user;
	}
	
	@Transactional
	public void update(User user) {
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원을 찾을 수 없습니다.");
				});
		
		//oAuth가 비어 있을 때만 패스워드, 이메일 수정 가능
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
	}
	
	@Transactional
	public int join(User user) {
		try{
			user.setRole(RoleType.USER);
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserService : join(): "+e.getMessage());
		}
		return -1;
	}

    public Page<User> findAll(Pageable pageable) {

		return userRepository.findAll(pageable);
    }

    /*
	 * @Transactional(readOnly = true) //Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
	 * public User login(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
	
}
