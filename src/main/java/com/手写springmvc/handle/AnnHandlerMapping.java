package com.手写springmvc.handle;

import com.手写springmvc.RequestMapping;
import com.手写springmvc.entity.RequestMappingInfo;
import org.springframework.beans.BeansException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnHandlerMapping implements HandlerMapping {

    private static Map<String, Object> map = new HashMap<>();

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }

    //创建bean之后执行
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMappingInfo info = createRequestMappingInfo(method, bean);
            map.put(info.getUrl(), info);
        }
        return false;
    }


    private RequestMappingInfo createRequestMappingInfo(Method method, Object bean) {
        RequestMappingInfo info = new RequestMappingInfo();
        if (method.isAnnotationPresent(RequestMapping.class)) {
            info.setMethod(method);
            info.setObj(bean);
            info.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        return info;
    }


}
