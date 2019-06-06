package com.leyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 枚举类异常信息
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    //单例对象
    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    Category_NOT_FOUND(404,"未查到商品分类"),
    BRAND_NOT_FOUND(404,"品牌未查到"),
    BRAND_SAVE_ERROR(404,"新增品牌失败"),
    UPLOAD_FILE_ERROR(500,"文件上传失败"),
    FILE_TYPE_NO_ALLOW(400,"文件类型不被允许"),
    ;
    private int code;//返回状态码
    private String msg;//错误消息字符串


}
