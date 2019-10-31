package com.ls.validator;

import com.ls.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @program: ls-security
 * @description: 注解实现业务逻辑类
 * 实现了 javax的 ConstraintValidator的接口 需要定义它的泛型， 有两个值， 第一个值是指定我需要去实现业务逻辑的注解类，第二个就是限定这个注解能够验证的数据类型
 * @author: Liang Shan
 * @create: 2019-10-30 11:34
 **/
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    /*
    * 这个类不需要去打@Compoment注解，实现了ConstraintValidator接口的类，Spring会自动把他注册到bean里,所以可以直接使用@Autowired注解
    * */
    @Autowired
    private HelloService helloService;

    /**
     * @Description: 初始化方法，在这个方法中可以进行一些初始化的处理
     * @Param: [constraintAnnotation]
     * @return: void
     * @Author: Liang Shan
     * @Date: 2019/10/30 0030
     */
    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("自定义注解实现初始化");
    }


    /**
     * @Description: 实现具体判断业务逻辑的方法
     * @Param: [s, constraintValidatorContext]
     * @return: boolean
     * @Author: Liang Shan
     * @Date: 2019/10/30 0030
     */
    @Override
    public boolean isValid(Object a, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("进入到判断体中");
        return false;
    }
}
