package com.手写mybatis.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapperWrapper implements Serializable {

    //insert/update/delete
    private String type;
    //返回值的类型
    private String resultType;
    //参数的类型
    private String paramType;
    //sql语句
    private String sql;

}
