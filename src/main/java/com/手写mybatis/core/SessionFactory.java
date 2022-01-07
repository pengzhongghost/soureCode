package com.手写mybatis.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

public class SessionFactory {

    private DataSource dataSource;

    private Map<String, Map<String, MapperWrapper>> env;

    public SessionFactory(String configPath) {
        env = new HashMap<>();
        //解析xml文件
        parseConfigXml(configPath);
    }

    private void parseConfigXml(String configPath) {
        try {
            //todo 1。解析数据源
            InputStream resource = SessionFactory.class.getClassLoader().getResourceAsStream(configPath);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resource);
            Element root = document.getRootElement();
            Element dataSourceElement = root.element("dataSource");
            dataSource = DataSourceFactory.createDataSource(dataSourceElement.getTextTrim());

            //todo 2。获取所有的mapper文件
            List mapperElements = root.elements("mapper");
            List<String> mapperPaths = new ArrayList<>();
            for (int i = 0; i < mapperElements.size(); i++) {
                Element element = (Element) mapperElements.get(i);
                mapperPaths.add(element.getTextTrim());
            }
            for (String mapperPath : mapperPaths) {
                Map<String, MapperWrapper> wrapper = new HashMap<>();
                Document doc = saxReader.read(SessionFactory.class.getClassLoader().getResourceAsStream(mapperPath));
                Element rootElement = doc.getRootElement();
                String namespace = rootElement.attribute("namespace").getValue();
                Iterator iterator = rootElement.elementIterator();
                while (iterator.hasNext()) {
                    Element next = (Element) iterator.next();
                    String type = next.getName();
                    String id = next.attribute("id").getValue();
                    String resultType = next.attribute("resultType").getValue();
                    String paramType = next.attribute("paramType").getValue();
                    String sql = next.getTextTrim();
                    wrapper.put(id, new MapperWrapper(type, resultType, paramType, sql));
                }
                env.put(namespace, wrapper);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Session openSession() {
        Session session = null;
        try {
            session = new Session(dataSource.getConnection(), env);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }


}
