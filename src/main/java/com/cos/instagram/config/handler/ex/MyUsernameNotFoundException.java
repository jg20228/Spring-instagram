package com.cos.instagram.config.handler.ex;

public class MyUsernameNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public MyUsernameNotFoundException() {
		this.message = super.getMessage();
	}
	
	public MyUsernameNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
