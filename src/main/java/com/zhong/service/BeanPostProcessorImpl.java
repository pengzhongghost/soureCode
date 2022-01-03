package com.zhong.service;

import com.spring.Component;
import com.spring.beanpostprocessor.BeanPostProcessor;


@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //System.out.println("初始化前");
        if (beanName.equals("userService")) {
            UserService userService = (UserService) bean;
            userService.setName("你好帅");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        //System.out.println("初始化后");
        return bean;
    }
}
