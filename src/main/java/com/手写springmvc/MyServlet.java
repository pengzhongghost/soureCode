package com.手写springmvc;

import com.手写springmvc.handle.HandlerAdapter;
import com.手写springmvc.handle.HandlerMapping;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class MyServlet extends HttpServlet {

    private String contextConfig;

    static Collection<HandlerMapping> handlerMappings;
    static Collection<HandlerAdapter> handlerAdapters;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config.getInitParameter("classpath:spring-servlet.xml"));
        //获取映射器
        Map<String, HandlerMapping> handlerMappingMap = context.getBeansOfType(HandlerMapping.class);
        handlerMappings = handlerMappingMap.values();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handlerMapping = getHandlerMapping(req);
        HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);
        Object result = null;
        try {
            result = handlerAdapter.handle(req, resp, handlerMapping);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter writer = resp.getWriter();
        writer.println(result);
    }

    /**
     * 获取适配器
     *
     * @param handle
     * @return
     */
    private HandlerAdapter getHandlerAdapter(Object handle) {
        if (!CollectionUtils.isEmpty(handlerAdapters)) {
            for (HandlerAdapter handlerAdapter : handlerAdapters) {
                if (handlerAdapter.support(handle)) {
                    return handlerAdapter;
                }
            }
        }
        return null;
    }

    private Object getHandlerMapping(HttpServletRequest request) {
        if (!CollectionUtils.isEmpty(handlerMappings)) {
            for (HandlerMapping handlerMapping : handlerMappings) {
                Object handler = handlerMapping.getHandler(request.getRequestURI());
                return handler;
            }
        }
        return null;
    }

}

