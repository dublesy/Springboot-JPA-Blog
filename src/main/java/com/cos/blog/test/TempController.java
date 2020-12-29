package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {
	@GetMapping("/temp/home")

	public String tempHome() {
		System.out.println("tempHome()");
		//파일을 리턴할 때의 기본경로:src/main/resources/static
		//리턴명 : "/home.html" 로 해야함
		//jsp를 사용하기 위해 뷰리졸버 설정함
		return "home.html";
	}

	@GetMapping("/temp/image")
	public String tempImg() {
		return "/a.png";
	}

	@GetMapping("/temp/jsp")
	public String tempJsp() {
		return "test";
	}
}
