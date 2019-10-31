package com.ls.dto;
import com.fasterxml.jackson.annotation.JsonView;
import com.ls.validator.MyConstraint;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * @program: ls-web
 * @description: 封装用户数据
 * @author: Liang Shan
 * @create: 2019-10-28 10:37
 **/
public class User {
    /*用户简单视图*/
    public interface UserSimpleView {
    };
    /*用户详细视图*/
    public interface UserDetailView extends UserSimpleView{
    };

    public User() {

    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    private String id;
    @MyConstraint(message = "用户名只能是小明")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Past(message = "时间不正确")
    private Date birthday;


    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    // 因为有继承关系，所以使用了UserDetailView的查询接口也会有username这个字段
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
