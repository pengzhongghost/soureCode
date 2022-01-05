package com.手写springmvc.handle;

import com.手写springmvc.RequestParam;
import com.手写springmvc.entity.RequestMappingInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnoHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return handler instanceof RequestMappingInfo;
    }

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        //todo 1.请求入参
        Map<String, String[]> paramMap = req.getParameterMap();
        RequestMappingInfo info = (RequestMappingInfo) handler;
        Method method = info.getMethod();
        //todo 2.方法的注解RequestParam的value
        Parameter[] params = method.getParameters();
        //获取到到结果
        Object[] result = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            for (Map.Entry<String, String[]> reqParam : paramMap.entrySet()) {
                if (reqParam.getKey().equals(params[i].getAnnotation(RequestParam.class).value())) {
                    //获取浏览器传到数据
                    result[i] = reqParam.getValue()[0];
                }
            }
        }
        return method.invoke(info.getObj(), result);
    }
}
