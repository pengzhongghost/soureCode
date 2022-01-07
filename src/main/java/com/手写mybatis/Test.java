package com.手写mybatis;

import com.手写mybatis.core.Session;
import com.手写mybatis.core.SessionFactory;
import com.手写mybatis.mapper.StudentMapper;
import com.手写mybatis.pojo.Student;

public class Test {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new SessionFactory("mybatis-config.xml");
        Session session = sessionFactory.openSession();
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        studentMapper.saveStudent(new Student(3L, "你好", 18, 1));
    }
}
