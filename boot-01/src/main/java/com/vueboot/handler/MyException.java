package com.vueboot.handler;

import com.vueboot.bean.ResultCode;
import lombok.Getter;

@Getter
public class MyException extends Exception{

    private ResultCode resultCode;

    public MyException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
