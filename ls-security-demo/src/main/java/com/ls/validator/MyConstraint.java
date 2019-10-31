package com.ls.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
* @Description: 自定义约束校验注解
* @Author: Liang Shan
* @Date: 2019/10/30 0030
*/
/**
 *@Target：注解可以使用的地方
 *@Retention(
 *1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 *2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 *3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 *@Constraint:校验实现类
 */
@Target({ElementType.METHOD, ElementType.FIELD})// @Target是为了定义这个注解可以标注在哪里，这里因为是需要做参数的校验，所以定义，可以标注在方法上和字段上
@Retention(RetentionPolicy.RUNTIME)// 运行时的一个注解
@Constraint(validatedBy = MyConstraintValidator.class)// javax校验注解，声明这个注解用于校验的，并且可以指定当前注解的具体业务逻辑实现类是哪一个类
public @interface MyConstraint {
    // 如果要实现javax的校验注解，就需要实现三个参数 第一个message， 第二个groups 第三个payload
    String message() default "{自定义约束校验注解消息}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
