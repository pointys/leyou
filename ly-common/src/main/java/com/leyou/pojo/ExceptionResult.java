package com.leyou.pojo;

import com.leyou.enums.ExceptionEnum;
import lombok.Data;

/**
 * 封装异常信息成对象
 */
@Data
public class ExceptionResult {

    private int status;
    private String msg;
    private Long timeStamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.msg = em.getMsg();
        this.timeStamp = System.currentTimeMillis();
    }
}
