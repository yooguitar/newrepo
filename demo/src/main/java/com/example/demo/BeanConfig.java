package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
	/*
	 * *** 스프링 프레임워크와 스프링 부트의 차별점 ***
	 * 
	 * Java기반 설정을 통해 사용해야하는 Bean을 정의할 수 있음
	 * 
	 * @Configuration
	 * 스프링의 설정 클래스를 정의할 때 사용
	 * 하나 이상의 @Bean이 달린 메서드를 포함해 스프링 컨테이너에 빈을 등록함.
	 * 
	 * @Bean
	 * @Configuration클래스 내에서 메서드에 적용되어 스프링 빈을 생성하고 관리
	 * 메서드 반환값이 스프링 컨테이너에 의해 빈으로 등록됨. 
	 * 
	 * 이로 인해 XML설정보다 빠른시점에 오류를 발견할 수 있고, 코드 기반이기 때문에 자동완성이나 수정이 용이하며,
	 * 설정 클래스 내에서 빈의 생성과정을 명확하게 정의 할 수 있음
	 *  
	 * ----------------------------------------
	 * 이전 방법
	 * root-context.xml
	 * <bean class="풀클래스 명">
	 * 	<property 필드값 세팅 />
	 * </bean>
	 */
	
	@Bean
	public TestBean testBean() {
		// Configuration 클래스에 메소드를 선언하고 @Bean 애노테이션을 달아주면 등록된다!
		// 메서드의 반환값이 Bean으로 등록된다
		return new TestBean();
	}
	
}
