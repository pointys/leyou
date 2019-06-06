package com.leyou.exception;

import com.leyou.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    //已封装在枚举类
//    private int code;
//    private String message;

    private ExceptionEnum exceptionEnum;


}
