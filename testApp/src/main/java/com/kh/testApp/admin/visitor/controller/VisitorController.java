package com.kh.testApp.admin.visitor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.testApp.admin.visitor.service.VisitorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("visitor")
@RequiredArgsConstructor
public class VisitorController {
	
	private final VisitorService service;

	@GetMapping
	public ResponseEntity<?> findVisitor() {	
		
		return null;
	}

	
	
	
}
