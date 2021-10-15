package com.cos.blog.controller;


import java.util.UUID;

import javax.servlet.ServletContext;

import com.cos.blog.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수 있는 경로는 /auth/** 허용
//그냥 주소가 / 이면 index.jsp 허용
//static 이하에 있는 /js/**, /css/**, /image/** 허용
@Controller
public class UserController {
	
	private final static String KAKAO_CLIENT_ID = "e53ece4615b948ce35e9ac43dbb64569";
	private final static String KAKAO_REDIRECT_URI ="http://localhost:8000/blog/auth/kakao/callback";
	private final static String KAKAO_REQUEST_TOKEN_URI="https://kauth.kakao.com/oauth/token";	
	private final static String KAKAO_REQUEST_USER_INFO_URI="https://kapi.kakao.com/v2/user/me";
		
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServletContext context;
	
	@Value("${cos.key}")
	private String cosKey;
	
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm(@AuthenticationPrincipal PrincipalDetail principal) {
		//로그인한 정보가 있으면 로그인 페이지로 못가게 하고, 인덱스 페이지로 이동하게 함
		if(principal != null) return "redirect:/";
		return "user/loginForm";
	}

	@GetMapping("/usersForm")
	public String usersForm(){
		System.out.println("요청 성공");
		return "user/usersForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		System.out.println("요청 성공");
		return "user/updateForm";
	}

	@GetMapping("/users")
	public String index(Model model, @PageableDefault(size = 10, sort="id", direction = Sort.Direction.DESC ) Pageable pageable) {
		Page<User> pagingUser = userService.findAll(pageable);
		model.addAttribute("users", pagingUser);
		return "user/users";
	}


	//data를 리턴해주는 컨트롤러 함수로 셋팅
	@GetMapping("/auth/kakao/callback")
	public String kakaoLoginCallback(String code) { 
		//POST 방식으로 key=value  데이터를 요청(카카오톡으로)
		//Retrofit2
		//OkHttp
		//RestTemplate		
		RestTemplate rt = new RestTemplate();
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("grant_type", "authorization_code");
		paramsMap.add("client_id", KAKAO_CLIENT_ID);
		paramsMap.add("redirect_uri",KAKAO_REDIRECT_URI);
		paramsMap.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(paramsMap, headers);
		
		//Http 요청하기(POST방식), reqponse 객체로 응답 받음
		ResponseEntity<String> response = rt.exchange(
				KAKAO_REQUEST_TOKEN_URI,
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				);
		
		//Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰 : "+ oAuthToken.getAccess_token());
		
		//RestTemplate		
				RestTemplate rt2 = new RestTemplate();
				//HttpHeader 오브젝트 생성
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
				
				//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
						new HttpEntity<>(headers2);
				
				//Http 요청하기(POST방식), reqponse 객체로 응답 받음
				ResponseEntity<String> response2 = rt2.exchange(
						KAKAO_REQUEST_USER_INFO_URI,
						HttpMethod.POST,
						kakaoProfileRequest,
						String.class
						);
				
				//Gson, Json Simple, ObjectMapper
				ObjectMapper objectMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}	
				//User 오브젝트 username, password, email
				System.out.println("카카오 id(번호) : " + kakaoProfile.getId());
				System.out.println("블로그 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
				System.out.println("블로그 이메일 : "+kakaoProfile.getKakao_account().getEmail());
				//UUID garbagePassword = UUID.randomUUID();
				//System.out.println("블로그 패스워드 : "+garbagePassword);
				System.out.println("블로그 패스워드 : "+cosKey);
				
				
				User kakaouser = User.builder()
						.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
						.password(cosKey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
				
				User orginUser = userService.find(kakaouser.getUsername());
				//가입자 혹은 비가입자 체크 해서 처리				
				if(orginUser == null) {
					System.out.println("기존 회원이 없기 때문에 자동 회원 가입을 진행합니다.");
					userService.join(kakaouser);	
				}
					
				// 로그인 처리
				Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), cosKey));
				SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
	
}
