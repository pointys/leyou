package com.leyou.advice;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.CustomException;
import com.leyou.pojo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 异常通知类
 */
@ControllerAdvice
public class ExceptionHandler {
    //捕获所有自定义异常
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResult> exceptionHandler(CustomException e) {
        //获取自定义异常对象(单例)
        ExceptionEnum eme = e.getExceptionEnum();
        //返回状态码和异常消息
        return ResponseEntity.status(eme.getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
