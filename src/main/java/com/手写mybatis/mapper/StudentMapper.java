package com.手写mybatis.mapper;

import com.手写mybatis.pojo.Student;

public interface StudentMapper {

    Integer saveStudent(Student student);

    Student findStudent(Long id);

}
