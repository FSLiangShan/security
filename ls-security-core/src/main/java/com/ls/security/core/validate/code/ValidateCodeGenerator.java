package com.ls.security.core.validate.code;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

public interface ValidateCodeGenerator<T extends ValidateCode> {
     T generate(ServletWebRequest request) throws ServletRequestBindingException, IOException;
}
