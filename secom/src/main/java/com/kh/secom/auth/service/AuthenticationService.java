package com.kh.secom.auth.service;

import java.util.Map;

import com.kh.secom.member.model.vo.MemberDTO;

public interface AuthenticationService {
	
	Map<String, String> login(MemberDTO requestMember);
	// access토큰과 refresh토큰 총 2개가 돌아가야한다. map에 담아서 돌려보내자

}
