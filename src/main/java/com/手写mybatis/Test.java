package com.手写mybatis;

import com.手写mybatis.core.Session;
import com.手写mybatis.core.SessionFactory;

public class Test {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new SessionFactory("mybatis-config.xml");
        Session session = sessionFactory.openSession();
        System.out.println(session);
    }
}
