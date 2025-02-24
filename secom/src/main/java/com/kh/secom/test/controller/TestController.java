package com.kh.secom.test.controller;

import org.springframework.stereotype.Controller;

@Controller
public class TestController {
	
	// 이거 잘 돌아가는지 테스트 해보고싶다..
	// => test밑에 똑같은 경로의 폴더 만듬
	public String testPrint(String argument) {
		return argument;
	}
	
	public int testPlus(int firstNum, int secondNum) {
		return firstNum + secondNum;
	}

	public void testException(int pk) {
		if(pk < 1) {
			throw new IllegalArgumentException();
		}
	}
	
}
