package com.ls.security.support;

import lombok.Data;

/**
 * @author: Liang Shan
 * @date: 2019-11-07 11:44
 * @description: 简单响应对象
 */
@Data
public class SimpleResponse {

    public SimpleResponse(Object content) {
        this.content = content;
    }
    private Object content;
    
}
