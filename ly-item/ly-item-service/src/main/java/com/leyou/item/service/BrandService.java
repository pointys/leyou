package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.CustomException;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String keywords) {
        if (rows != -1) {
            // 1.PageHelper分页插件，拦截
            PageHelper.startPage(page, rows);
        } else {
            //rows=-1不分页
        }
        // 2.实例模板对象设查询条件
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(keywords)) {
            //2.1条件不为空createCriteria设置查询条件
            example.createCriteria().orLike("name", "%" + keywords + "%")//模糊条件
                    .orEqualTo("letter", keywords.toUpperCase());//keywords转换成大写
        }
        if (StringUtils.isNotBlank(sortBy)) {
            //2.2排序 order by id desc (sortBy传回id或letter字符串)
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");//空格
            example.setOrderByClause(orderByClause);
        }

        // 3.查询模板对象
        List<Brand> brands = brandMapper.selectByExample(example);
        brands.forEach(System.out::println);
        if (CollectionUtils.isEmpty(brands)) {
            //查询对象为空 抛出自定义异常
            throw new CustomException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        // 4.pageHelper解析分页信息获取查询总条数不需再查询数据库
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        // 5.返回封装结果
        return new PageResult<>(pageInfo.getTotal(), brands);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //1.新增品牌表
        //id自动增长不需要传参
        brand.setId(null);
        int i = brandMapper.insert(brand);
        if (i != 1) {
            throw new CustomException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //2.新增中间表
        for (Long cid : cids) {
            i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (i != 1) {
                throw new CustomException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }

    /**
     * 根据category id查询brand
     * @param cid
     * @return
     */
    public List<Brand> queryBrandByCategoryId(Long cid) {

        return this.brandMapper.queryBrandByCategoryId(cid);
    }

    /**
     * 根据品牌id集合查询品牌信息
     * @param ids
     * @return
     */
    public List<Brand> queryBrandByBrandIds(List<Long> ids) {
        return this.brandMapper.selectByIdList(ids);
    }

}
