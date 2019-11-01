package com.ls.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ls.dto.User;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        return new User("1", "tom", "123456");
    }

    @PutMapping("/{id:\\d+}")// 正则表达式限定参数
    @JsonView(User.UserDetailView.class)// 指定方法返回详细对象视图，有username和password
    public User update(@Valid @RequestBody User user) {
        return user;
    }

    @PostMapping
    public User create( @RequestBody User user) {
        throw new RuntimeException("创建用户异常");
    }

    @DeleteMapping("/{id:\\d+}")
    public int delete(@PathVariable(value = "id")String id) {
        System.out.println(id);
        return 1;
    }
    
}
