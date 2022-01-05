package com.手写springmvc.handle;

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public interface HandlerMapping extends InstantiationAwareBeanPostProcessor {

    Object getHandler(String url);

}
