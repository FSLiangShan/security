package com.ls.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ls.dto.User;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: ls-web
 * @description: 用户Controller层
 * @author: Liang Shan
 * @create: 2019-10-28 10:15
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(e -> System.out.println(e.toString()));
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println("hahaha" + user.getBirthday());
        user.setId("1");
        return user;
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)// 指定方法返回简单对象视图，只有username
    public List<User> query(@RequestParam String username, @PageableDefault(size = 20, page = 0, sort = "username") Pageable pageable) {
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());
        List<User> users = Lists.newArrayList();
        users.add(new User("a", "132"));
        users.add(new User("b", "456"));
        users.add(new User("c", "789"));
        return users;
    }

    @GetMapping("/{id:\\d+}")// 正则表达式限定参数
    @JsonView(User.UserDetailView.class)// 指定方法返回详细对象视图，有username和password
    public User getInfo(@PathVariable String id) {
        User user = new User("a", "132");
        user.setUsername("tom");
        return user;
    }

    @PutMapping("/{id:\\d+}")// 正则表达式限定参数
    @JsonView(User.UserDetailView.class)// 指定方法返回详细对象视图，有username和password
    public User update(@Valid@RequestBody User user) {
        return user;
    }

}
