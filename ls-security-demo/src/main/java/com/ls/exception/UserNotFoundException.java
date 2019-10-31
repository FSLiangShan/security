package com.ls.exception;

import lombok.Data;

/**
 * @program: ls-security
 * @description:
 * @author: Liang Shan
 * @create: 2019-10-31 15:50
 **/
@Data
public class UserNotFoundException extends RuntimeException {
    private String id;

    public UserNotFoundException(String id) {
        super("class not found");
        this.id = id;
    }


}
