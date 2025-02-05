package com.kh.secom.configuration.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.secom.auth.util.JwtFilter;

import lombok.RequiredArgsConstructor;

// **사용할 시큐리티 필터를 빈으로 등록하자
// 필터를 사용할 것과 아닌 것을 구분하자
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // method 보안 기능을 활성화 한다
public class SecurityConfigure {

	private final JwtFilter filter;
	
	// @Bean: 스프링 부트의 빈 등록 방법. 자동으로 등록해준다.
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		// Cors 설정에 필요한 객체들 set 하는 작업.
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));// import java.util.Arrays
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		configuration.setAllowCredentials(true); // == 자격 증명 허용하겠다.
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // import UrlBased
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean // Bean 애노테이션을 이용해서 빈으로 등록하는 경우 동일한 이름의 메소드가 존재하면 안됨! 절대 안됨!
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		/*
		 * 방법 1. return httpSecurity.formLogin().disable().build(); 시큐리티 필터 체인을 만드는 방법임.
		 * 시큐리티는 사용하되 formLogin()은 사용하지 않겠다! 하지만 메소드 체이닝하는 방법은 7.0에서 사라질 문법이므로 사용 안할 것
		 * 
		 * 방법 2. return httpSecurity.formLogin(new
		 * Customizer<FormLoginConfigurer<HttpSecurity>>() {
		 * 
		 * @Override public void customize(FormLoginConfigurer<HttpSecurity> formLogin)
		 * { formLogin.disable(); } }).httpBasic(null).csrf(null).cors(null).build();
		 * 대안으로 익명클래스를 구현하는 방법을 사용했다. 하지만 이 방법은 제외할 클래스가 많을 경우 중복 코드가 많고, 코드가 복잡하기 때문에
		 * 부모 클래스(AbstractHttpConfigurere)를 상속받아 다형성을 적용하는 방법을 사용할 것
		 */

		return httpSecurity.formLogin(AbstractHttpConfigurer::disable) // form 로그인 방식은 사용하지 않겠다.
				.httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용 안하겠다
				.csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
				.cors(Customizer.withDefaults()) // 앞,뒤 분리해서 작업한다. 일단 꺼놓고 나중에 nginx 붙이기, import security
				.authorizeHttpRequests(requests -> {
					requests.requestMatchers("/members", "/members/login", "/uploads/**").permitAll(); // 인증 없이 이용 가능
					requests.requestMatchers(HttpMethod.PUT, "/members").authenticated(); // 인증 해야 이용 가능
					requests.requestMatchers("/admin/**").hasRole("ADMIN"); // admin 권한만 사용 가능
					requests.requestMatchers(HttpMethod.DELETE, "/members").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/members/refresh").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/boards").authenticated();
					requests.requestMatchers(HttpMethod.GET, "/boards/**", "/comments/**").permitAll();
					requests.requestMatchers(HttpMethod.PUT, "/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.DELETE, "/boards/**").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/comments").authenticated();
				})
				/*
				 * sessionManagement : 세션 관리에 대한 설정을 지정할 수 있음 sessionCreatePolicy : 정책을 설정
				 */
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// BCrypt를 이미 시큐리티가 가지고 있으므로 호출하여 인스턴스 생성 후 리턴만 하면 끝
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		// 인증 절차를 수행하는 메소드(인터페이스). 매개변수로 인증정보의 토큰을 받아야 한다.
	}

}
