package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

//나중에 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고
//완료가 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세선저장소에
//저장을 해준다. 
@Getter
public class PrincipalDetail implements UserDetails{

	private User user; //콤포지션 (객체를 품고 있는 것)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지 리턴한다.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있는지 않았는지 리턴
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 활성화가 되었는지 안되었는지 리턴
	@Override
	public boolean isEnabled() {
		return true;
	}

	//계정이 어떤 권한을 가졌는지 권한을 리턴함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			
//			@Override
//			public String getAuthority() {
//				return "ROLE_"+user.getRole();
//			}
//		});
		//권한이 여러개이면 List를 for문 돌려서 컬렉터에 add 해야함
		collectors.add(()->{
			return "ROLE_"+user.getRole();
			});
		return collectors;
	}

}
