package com.手写springmvc.handle;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BeanIdHandlerMapping implements HandlerMapping {

    private static Map<String, Object> map = new HashMap<>();

    //创建bean之后执行
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.startsWith("/")) {
            map.put(beanName, bean);
        }
        return false;
    }

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }

}
