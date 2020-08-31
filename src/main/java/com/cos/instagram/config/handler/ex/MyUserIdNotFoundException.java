package com.cos.instagram.config.handler.ex;

//My는 Spring이 만든것이랑 충돌날까봐
public class MyUserIdNotFoundException extends RuntimeException{
	//extends RuntimeException 실행시 발생할수있는 것들이 모여있음.
	private String message;
	
	public MyUserIdNotFoundException() {
		//메시지 없을때를 대비한 오버로딩해서 만든 생성자
		this.message = "해당 유저의 id를 찾을 수 없습니다.";
	}
	
	public MyUserIdNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		//super.getMessage(); 는 null값
		return message;
	}
	
}
