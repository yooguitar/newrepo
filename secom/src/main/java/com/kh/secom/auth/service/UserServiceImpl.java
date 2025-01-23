package com.kh.secom.auth.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.member.model.mapper.MemberMapper;
import com.kh.secom.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
	// AuthenticationManager가 실질적으로 사용자의 정보를 조회하는데 사용할 클래스

	private final MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		// 토큰에서 뽑힌 username은 여기로 먼저 온다
		// 시큐리티에서 사용되는 Username은 각 사용자를 식별하기 위한 값이다(pk, unique)
		Member user = mapper.findByUserId(username);
		if(user == null) {throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");}
		// 사용자가 입력한 아이디값이 테이블이 존재하긴 함
		// 여기서 authenticationManager에 DB에서 조회된 정보를 돌려줘야 하지만
		// 이 메소드의 반환타입은 이미 정해져있다. == UserDetails
		// 값을 돌려주기 위해 CustomUserDetails 클래스를 만들었음 (userNo도 줘야하는 이유)
		return CustomUserDetails.builder()
								.userNo(user.getUserNo())
								.username(user.getUserId())
								.password(user.getUserPwd())
								.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
								.build();
	}
	
	
	
	
	
}
