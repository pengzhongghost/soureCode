package com.手写mybatis.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

public class SqlInvocationHandler implements InvocationHandler {

    private Connection connection;

    private Map<String, MapperWrapper> env;

    public SqlInvocationHandler(Connection connection, Map<String, MapperWrapper> env) {
        this.connection = connection;
        this.env = env;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method.getName() saveStudent
        //拿到sql包装类
        MapperWrapper mapperWrapper = env.get(method.getName());
        PreparedStatement statement = connection.prepareStatement(mapperWrapper.getSql());
        switch (mapperWrapper.getType()) {
            case "insert":
                Class<?> aClass = Class.forName(mapperWrapper.getParamType());
                //第一个参数所有的字段
                Field[] fields = args[0].getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    statement.setObject(i + 1, fields[i].get(args[0]));
                }
                return statement.executeUpdate();
            default:
                throw new RuntimeException();
        }
    }
}
