package com.手写mybatis.core;

import com.手写mybatis.DataSourceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;

public class SessionFactory {

    private DataSource dataSource;

    public SessionFactory(String configPath) {
        //解析xml文件
        parseConfigXml(configPath);
    }

    private void parseConfigXml(String configPath) {
        try {
            InputStream resource = SessionFactory.class.getClassLoader().getResourceAsStream(configPath);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resource);
            Element root = document.getRootElement();
            Element dataSourceElement = root.element("dataSource");
            dataSource = DataSourceFactory.createDataSource(dataSourceElement.getTextTrim());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
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
