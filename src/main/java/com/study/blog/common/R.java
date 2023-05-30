package com.study.blog.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 用于做前端交互的类
 * @param <T>
 */
@Data
public class R<T> implements Serializable {
    private Integer code;// 编码,1为成功,其他数字为失败
    private String msg;// 错误信息
    private T data;// 数据
    private HashMap map = new HashMap();// 动态数据

    public static <T> R<T> success(T object){
        R<T> r = new R<>();
        r.code = 1;
        r.data = object;
        return r;
    }
    public static <T> R<T> error(String msg){
        R<T> r = new R<>();
        r.code = 0;
        r.msg = msg;
        return r;
    }

    public R<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
}
