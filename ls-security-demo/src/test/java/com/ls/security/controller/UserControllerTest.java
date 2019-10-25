package com.ls.security.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

/**
 * @program: ls-security
 * @description: 伪造MVC环境 模拟HTTP请求对controller进行测试
 * @author: Liang Shan
 * @create: 2019-10-25 13:32
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    /** 
    * @Description: 在每一个测试用例之前执行,构建mvc
    * @Param: [] 
    * @return: void 
    * @Author: Liang Shan 
    * @Date: 2019/10/25 0025 
    */ 
    @Before
    public void setup() {
        // 创建伪造
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(webApplicationContext);
        mockMvc =defaultMockMvcBuilder.build();
    }
    @Test
    public void whenQuerySuccess() throws Exception {
        // 模拟发出get的 url地址为 /user的请求，设置contentType 为 json_utf8
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        //  执行测试用例
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        // 判断期望结果返回状态是否正常
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
        
    }

}
