package com.ls.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author: Liang Shan
 * @date: 2019-11-04 14:54
 * @description: 异步服务处理请求
 */
@RestController
@RequestMapping("async")
@Slf4j
public class AsyncController {



    /**
    * @author: Liang Shan
    * @date: 2019-11-04
    * @time: 14:57
    * @param:
    * @description: 同步方式
    */
    @GetMapping
    public String order() throws InterruptedException {
        log.info("runnable主线程开始处理服务");
        // 假装在处理订单服务
        Thread.sleep(1000);
        log.info("runnable主线程结束处理服务");
        return "success";
    }

    /**
     * @author: Liang Shan
     * @date: 2019-11-04
     * @time: 15:00
     * @param: null
     * @description: 以下订单为例，试用Runnable多线程处理方式
     * 使用Callable来处理 ，Callable其实就是在当前线程新建一个线程，spring进行线程的管理
     * java5开始，提供了Callable接口，是Runable接口的增强版。同样用Call()方法作为线程的执行体，增强了之前的run()方法。因为call方法可以有返回值，也可以声明抛出异常。
     *
     *    1.创建Callable的实现类,并重写call()方法，该方法为线程执行体，并且该方法有返回值
     *
     *   2.创建Callable的实例，并用FutuerTask类来包装Callable对象，该FutuerTask封装了Callable对象call()方法的返回值
     *
     *   3.实例化FutuerTask类，参数为FutuerTask接口实现类的对象来启动线程
     *
     *   4.通过FutuerTask类的对象的get()方法来获取线程结束后的返回值
     */
    @GetMapping("/runnable")
    public Callable<String> orderRunnable() {
        log.info("runnable主线程开始处理服务");
        Callable<String>result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("runnable副线程开始处理服务");
                // 继续假装在处理订单服务
                Thread.sleep(1000);
                log.info("runnable副线程结束处理服务");
                return "success";
            }
        };
        log.info("runnable主线程结束处理服务");
        return result;
    }


}
