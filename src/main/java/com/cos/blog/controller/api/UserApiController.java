package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PutMapping("/user")
	public ResponseDto<Integer> Update(@RequestBody User user) {
		userService.update(user);
		//여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경되었지만
		// 세션값은 변경되지 않았기 때문에 로그아웃하고 다시 들어가야지만
		//세션값이 변경 된 것을 확인 할 수 있음
		
		//세션 등록
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController save() 호출");
		
		int result = userService.join(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); //자바오브젝트를 잭슨 라이브러리가 JSON으로 변환해서 리턴해줌
	}
	/** 
	 *전통적인 방식의 로그인
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session){
	 * System.out.println("UserApiController login() 호출");
	 * 
	 * User principal = userService.login(user); // principal(접근주체); if(principal !=
	 * null) { session.setAttribute("principal", principal); } return new
	 * ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
	 */
}
