package com.vueboot.handler;

import com.vueboot.bean.Result;
import com.vueboot.bean.ResultCode;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception ex) {
        if(ex.getClass() == MyException.class) {
            //类型转型
            MyException ce = (MyException) ex;
            Result result = new Result(ce.getResultCode());
            return result;
        }else{
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public Result error(AuthorizationException ex) {
        return new Result(ResultCode.UNAUTHORISE);
    }
}
