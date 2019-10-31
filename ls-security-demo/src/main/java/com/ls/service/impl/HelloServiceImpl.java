package com.ls.service.impl;

import com.ls.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @program: ls-security
 * @description:
 * @author: Liang Shan
 * @create: 2019-10-30 11:48
 **/
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hi";
    }
}
