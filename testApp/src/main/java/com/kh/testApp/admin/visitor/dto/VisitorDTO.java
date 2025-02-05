package com.kh.testApp.admin.visitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitorDTO {
	private int dailyVisitor;
	private int weeklyVisitor;
	private int monthlyVisitor;
}
