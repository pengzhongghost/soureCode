package com.zhong;

import com.spring.MyApplicationContext;
import com.zhong.service.UserService;

public class Test {

    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);
        UserService userService01 =(UserService)myApplicationContext.getBean("userService");
        userService01.test();

    }

}
