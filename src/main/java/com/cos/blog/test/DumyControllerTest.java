package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DumyControllerTest {

	@Autowired //의존성주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 ID "+id+"는 DB에 없습니다.";
		}
		
		return "해당 아이디는 삭제되었습니다. ID : "+id;
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
		
		System.out.println("id : "+ id);
		
		System.out.println("password : "+reqUser.getPassword());
		System.out.println("email : "+reqUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("수정에 실패하였습니다."); 
					});
		
		user.setPassword(reqUser.getPassword());
		user.setEmail(reqUser.getEmail());
		//save 함수는 id를 전달하지 않으면, insert를 해주고
		//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 하고, 없으면 insert를 한다
		//userRepository.save(user);
		//영속성 컨텍스트, 1차 캐싱, 플러시 처리
		//더티체킹 @Transactional 을 걸면 save를 하지 않아도 update가 된다
		
		return user;
	}
	
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 1, sort="id", direction = Sort.Direction.DESC )Pageable pageable ){
		Page<User> users = userRepository.findAll(pageable);
		
		//List<User> users = users.getContent();

		return users;
		
	}
	
	//전체를 다 받을거임
	@GetMapping("/dummy/users")
	public List<User> list(){
			return userRepository.findAll();
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user를 찾으면 내가 데이터베이스에서 못찾으면 null임
		//그럼 리턴을 할 때 null이 리턴되면 프로그램에 문제가 있지 않겠니?
		// Optional로 너의 User객체를 감사서 가져올테니 null인지 아닌지 판단해서 리턴해라
		//람다식으로 하면 더 간략하게 만들 수 있다. ()->{ IllegalArgumentException("해당 유저는 없습니다. id : "+id); )}
		User user =  userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});
		//요청:웹브라우저 리턴은 data
		//user 객체 = 자바오브젝트 -> JSON 웹브라우저가 이해하는 객체로 변환 -> Gson 라이브러리 사용 했는데 
		//스프링부트는 Message Converter라는 애가 응답시에 자동 작동을 한다.
		//만약에 자바 오브젝트를 리턴하게 되명 Message Converter가 Jackson 라이브러리를 호출해서 User 오브젝트를 Json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {		
		System.out. println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
