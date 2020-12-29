package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파ㅣㄹ)
// @Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	@GetMapping("/http/lombok")	
	public String lombokTest() {
		//Member m = new Member();
		Member m = Member.builder().username("1234").password("1234").id(4321).build();
		System.out.println("롬복 테스트 : "+m.toString());
		return "lombok test 완료";
	}
	
	@GetMapping("/http/get")	
	public String getTest(Member m) {
		return "get 요청 : "+m.getId()+" username : "+m.getUsername()+" password : "+m.getPassword();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청 : "+m.getId()+" username : "+m.getUsername()+" password : "+m.getPassword()+" email : "+m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+" username : "+m.getUsername()+" password : "+m.getPassword()+" email : "+m.getEmail();
	}
	
	@DeleteMapping("http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
