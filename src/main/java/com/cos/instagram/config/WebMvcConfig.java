package com.cos.instagram.config;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cos.instagram.config.auth.Cos;
import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;

import lombok.RequiredArgsConstructor;

//web.xml = WebMvcConfigurer
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer{

	private final HttpSession httpSession;
	
	//여기서 대부분 필터를 걸 수 있다.
	
	//addArgumentResolvers = GetMapping된 함수가 있을건데 
	//함수의 파라미터를 읽어서 분석하는 Prehandle이다. AOP임. AOP배울때 ->get시그니쳐라는 함수
	
	//resolvers.add으로 새로운거 계속 만들 수 있음
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		//cos
		resolvers.add(new HandlerMethodArgumentResolver() {
			
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				boolean isCosAnnotation = parameter.getParameterAnnotation(Cos.class) != null;
				return isCosAnnotation;
			}
			
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				return "cos";
			}
		});
		/////LoginUserAnnotation
		resolvers.add(new HandlerMethodArgumentResolver() {
			//내가 핸들링 한다는 것
			//만약 이 이름을 못찾는다면 내가 직접 AOP를 만들면 된다.
			
			//무슨 메소드를 호출해도 항상 동작한다.
			
			//1.supportsParamerter()에서 true가 리턴되면
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				//항상 메소드가 실행될때 파라미터를 가져온다.
				boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUserAnnotation.class) != null;
				boolean isUserClass = LoginUser.class.equals(parameter.getParameterType());//LoginUser 타입인지 체크
				return isLoginUserAnnotation && isUserClass;//이게 true일때 resolveArgument 실행
			}
			
			// 2. 세션을 만들어서 @LoginUserAnnotation 에 주입해준다.
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				return httpSession.getAttribute("loginUser");
			}
		});
	}
}
