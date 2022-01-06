package com.手写mybatis.core;

import java.sql.Connection;
import java.sql.SQLException;

public class Session {

    private Connection connection;

    public Session(Connection connection) {
        this.connection = connection;
    }

    public void begin(){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
