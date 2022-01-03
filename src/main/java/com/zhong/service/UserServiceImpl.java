package com.zhong.service;

import com.spring.Autowired;
import com.spring.Component;
import com.spring.Scope;

@Component("userService")
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void test() {
        System.out.println("test方法");
        test01();
        test02();
    }

    public void test01() {
        System.out.println(1 / 0);
    }

    public void test02() {
        throw new RuntimeException("哈利路亚");
    }

}
