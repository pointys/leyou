package com.leyou.item.controller;

import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import com.leyou.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 条件查询品牌列表
     * 前端vue get请求传回5个路径参数
     * 没用vo对象封装接收参数
     *
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param keywords
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "keywords", required = false) String keywords) {
        System.out.println("搜索参数========" + keywords+page+rows+sortBy+desc);

        PageResult<Brand> result = this.brandService.queryBrandByPageAndSort(page, rows, sortBy, desc, keywords);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("jjjjjj:"+result.toString());

        System.out.println ("总条数"+result.getTotal ());
        System.out.println ("总页数"+result.getTotalPage ());

        return ResponseEntity.ok(result);
    }

    /**
     * 保存商品品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("categories") List<Long> cids) {
        brandService.saveBrand(brand, cids);
        //CREATED状态码201 无返回值
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据category的id查询品牌信息
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryId(@PathVariable("cid") Long cid){
        List<Brand> list = this.brandService.queryBrandByCategoryId(cid);
        if (list == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据品牌id集合，查询品牌信息
     * @param ids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        List<Brand> list = this.brandService.queryBrandByBrandIds(ids);
        if (list == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
}
