package com.ls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: ls-security
 * @description:
 * @author: Liang Shan
 * @create: 2019-10-28 11:01
 **/
//(exclude = {SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableSwagger2
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
