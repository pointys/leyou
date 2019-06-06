package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接口对外暴露实体类 service实现直接调用
 */
@Data
@Table(name="tb_category")
public class Category {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private Long parentId;
    //isParent自动生成会把is漏掉 注意自己补全
    private Boolean isParent;
    private Integer sort;

}

