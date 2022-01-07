package com.手写mybatis.core;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class Session {

    private Connection connection;

    private Map<String, Map<String, MapperWrapper>> env;

    public Session(Connection connection, Map<String, Map<String, MapperWrapper>> env) {
        this.connection = connection;
        this.env = env;
    }

    public void begin() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public <T> T getMapper(Class<T> aClass) {
        T t = (T) Proxy.newProxyInstance(Session.class.getClassLoader(), new Class[]{aClass}, new SqlInvocationHandler(connection, env.get(aClass.getName())));
        return t;
    }

}
