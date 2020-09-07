package com.cos.instagram.config.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instagram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instagram.config.handler.ex.MyUsernameNotFoundException;
import com.cos.instagram.util.Script;

@RestController // 메시지만 return 해줄 것이라서 상관없음
@ControllerAdvice // 모든 Exception을 낚아챔
public class GlobalExceptionHandler {

	// Exception을 낚아채면 하나하나 처리가 귀찮아서 작은거 하나씩 잡는다.
	@ExceptionHandler(value = MyUserIdNotFoundException.class)
	public String myUserIdNotFoundException(Exception e) {
		return Script.back(e.getMessage());
		// 안드로이드,리액트는 응답할때 이렇게 해주면 안됨(ex : 히스토리백)
	}

	@ExceptionHandler(value = MyUsernameNotFoundException.class)
	public String myUsernameNotFoundException(Exception e) {
		return Script.alert(e.getMessage());
	};
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public String myIllegalArgumentException(Exception e) {
		return Script.alert(e.getMessage());
	}
}
