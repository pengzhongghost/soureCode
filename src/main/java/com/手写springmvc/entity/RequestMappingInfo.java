package com.手写springmvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMappingInfo {

    private Method method;

    private Object obj;

    private String url;

}
