package com.cos.instagram.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//어디에서 사용할 수 있는지 정의함 
@Retention(RetentionPolicy.RUNTIME)//실행시점을 정하는것인데 RUNTIME으로 정함.
public @interface LoginUserAnnotation {

}
