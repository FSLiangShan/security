package com.ls.web.controller;

import com.ls.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ls-security
 * @description: 控制器错误处理器
 * @author: Liang Shan
 * @create: 2019-10-31 16:14
 **/
@ControllerAdvice //打上这个注解，这个类就变成了一个切面类，经过controller所有的Exception都会经过这个切面类，寻找下面对应的ExceptionHandler进行处理
@ResponseBody
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler // 处理的异常类注解，匹配异常参数进入该方法
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 可以在
    public Map<String, Object> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", ex.getId());
        result.put("message", ex.getMessage());
        // 这里可以记录log
        // log.error("用户不存在",ex);
        return result;
    }
    // 也可以对其他的异常在这里集中进行处理，就不用写太多的try catch了
}
