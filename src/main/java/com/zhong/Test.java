package com.zhong;

import com.spring.MyApplicationContext;

public class Test {

    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);
        Object userService01 = myApplicationContext.getBean("userService");
        Object userService02 = myApplicationContext.getBean("userService");
        System.out.println(userService01);
        System.out.println(userService02);
    }

}
