package com.手写mybatis.core;

import com.手写mybatis.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SessionFactory {

    private DataSource dataSource;

    public SessionFactory(String type) {
        dataSource = DataSourceFactory.createDataSource(type);
    }

    public Session openSession() {
        Session session = null;
        try {
            session = new Session(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }


}
