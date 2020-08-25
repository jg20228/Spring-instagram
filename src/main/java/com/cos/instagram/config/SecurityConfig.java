package com.cos.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder encode() {
		//메모리에 띄우기만해도 되서 InstagramApplication.java에서도 가능
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();//postman 테스트할려고 해둠
		
		//모든 주소를 다 잠구기 ->.antMatchers("/**")
		http.authorizeRequests()
		.antMatchers("/","/user/**", "/follow/**", "/image/**")
		.authenticated()
		.antMatchers("/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		.anyRequest()
		.permitAll()
		.and()
		.formLogin()
		.loginPage("/auth/loginForm")//login이나 join은 인증이 필요없음 
		
		;
	}

}
