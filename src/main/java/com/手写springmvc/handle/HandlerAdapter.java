package com.手写springmvc.handle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 适配器
 */
public interface HandlerAdapter {

    boolean support(Object handler);

    Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException, IOException, InvocationTargetException, IllegalAccessException;

}
