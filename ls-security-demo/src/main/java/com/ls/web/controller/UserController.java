package com.ls.web.controller;

import com.ls.dto.User;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ls-web
 * @description: 用户Controller层
 * @author: Liang Shan
 * @create: 2019-10-28 10:15
 **/
@RestController
public class UserController {
    @GetMapping("/user")
    public List<User> query(@RequestParam  String username, @PageableDefault(size = 20, page = 0, sort = "username") Pageable pageable) {
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());
        List<User> users = Lists.newArrayList();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @GetMapping("/user/{id:\\d+}")
    public User getInfo(@PathVariable String id) {
        User user = new User();
        user.setUsername("tom");
        return user;
    }


}
