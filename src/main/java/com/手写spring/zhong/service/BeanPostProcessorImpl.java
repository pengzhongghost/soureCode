package com.手写spring.zhong.service;

import com.手写spring.spring.Component;
import com.手写spring.spring.beanpostprocessor.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //System.out.println("初始化前");
        if (beanName.equals("userService")) {
            UserServiceImpl userService = (UserServiceImpl) bean;
            userService.setName("你好帅");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            //jdk动态代理
            Object proxyInstance = Proxy.newProxyInstance(BeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理逻辑");
                    Object invoke = null;
                    try {
                        invoke = method.invoke(bean, args);
                    } catch (Exception e) {
                        System.out.println(e.getCause());
                    }
                    return invoke;
                }
            });
            return proxyInstance;
        }
        //System.out.println("初始化后");
        return bean;
    }
}
