package com.leyou.item.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

@Data//小辣椒
@Table(name = "tb_brand")//声明该实体类对应tb_brand数据库表
public class Brand {
    @Id//主键字段
//    @GeneratedValue(strategy = GenerationType.IDENTITY)//JAP用于标注主键的生成策略
    @KeySql(useGeneratedKeys=true)//通用mapper 生成主键的策略
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;


    /*注意点*/
//    @Column(name="name")//如果和数据库表字段名不一致
//    private String ssname;// 数据库表name字段对应该实体类ssname

//    @TableField(exist = false)类中有而数据库表中没有的属性
//    private String state;
}
