package com.kh.secom.member.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secom.exception.DuplicateUserException;
import com.kh.secom.exception.InvalidParameterException;
import com.kh.secom.member.model.mapper.MemberMapper;
import com.kh.secom.member.model.vo.Member;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService { // 일반사용자용 가입 메소드
	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void save(MemberDTO requestMember) {
		// 1. 빈 문자열 유효성 검사
		if("".equals(requestMember.getUserId()) || "".equals(requestMember.getUserPwd())) {
			throw new InvalidParameterException("유효하지 않은 값입니다.");
		}
		// 2. DB에 이미 사용자가 입력한 사용자가 존재해서는 안됨
		Member searched = memberMapper.findByUserId(requestMember.getUserId());
		if(searched != null) {
			throw new DuplicateUserException("이미 존재하는 아이디 입니다.");
		}
		// 3. 비밀번호 평문이라 그냥 들어가면 안됨
		// + ROLE컬럼 USER로 저장할 것
		Member member = Member.builder().userId(requestMember.getUserId())
										.userPwd(passwordEncoder.encode(requestMember.getUserPwd()))
										.role("ROLE_USER")
										.build(); 
		memberMapper.save(member);
		//log.info("회원가입 성공!");
	}

}
