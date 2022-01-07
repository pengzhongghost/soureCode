package com.手写mybatis.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 数据源工厂
 */
public class DataSourceFactory {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceFactory.createDataSource("druid");
        System.out.println(dataSource);
    }

    public static DataSource createDataSource(String type) {
        DataSource dataSource = null;
        Properties properties = new Properties();
        if ("hikari".equals(type)) {
            try {
                properties.load(DataSourceFactory.class.getClassLoader().getResourceAsStream("hikari.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            HikariConfig hikariConfig = new HikariConfig(properties);
            dataSource = new HikariDataSource(hikariConfig);
        } else if ("druid".equals(type)) {
            try {
                properties.load(DataSourceFactory.class.getClassLoader().getResourceAsStream("druid.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.configFromPropety(properties);
            dataSource = druidDataSource;
        }
        return dataSource;
    }


}
