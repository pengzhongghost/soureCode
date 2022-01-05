package com.手写spring.zhong;

import com.手写spring.spring.MyApplicationContext;
import com.手写spring.zhong.service.UserService;

public class Test {

    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);
        UserService userService01 =(UserService) myApplicationContext.getBean("userService");
        userService01.test();
    }

}
