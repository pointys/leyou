package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 我们仅需要实际数据,不需要返回html页面
 * RestController返回json数据 axios回调使用
 * ResponseEntity包括状态码、头部信息以及相应体内容
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点查询商品类目
     * rest风格要返回ResponseEntity类型
     * @param pid parent_id
     * @return rest风格List<Category>
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(
            @RequestParam(value = "pid", defaultValue = "0") Long pid) {
        List<Category> list = this.categoryService.queryCategoryByPid(pid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);//返回200状态码和list主体信息 简写方式
    }
    /**
     * 根据bid（品牌id）查询商品的信息
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid")Long bid){
        List<Category> list = this.categoryService.queryCategoryByBid(bid);
        if (list==null||list.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 添加商品分类的方法
     *
     * @param category
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCategory(Category category) {
        category.setId(null);
        int result = categoryService.addCateGory(category);
        if (result != 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteCategory(
            @RequestParam("id") Long id
    ) {
        int result = categoryService.deleteCateGory(id);
        if (result != 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 修改商品分类的方法
     *
     * @param id
     * @param name
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(@RequestParam("id") Long id, @RequestParam("name") String name) {
        int result = categoryService.updateCategory(id, name);
        if (result != 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 根据分类id的集合查询分类名称的集合
     * @param list
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryCategoryNamesBycids(
            @RequestParam("ids") List<Long> list
    ){
        List names = this.categoryService.queryCategoryNameByCids(list);
        if (names==null||names.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(names);
    }

    /**
     * 根据分类的cid查询所有的分类对象
     * @param cids
     * @return
     */
    @GetMapping("categories")
    public ResponseEntity<List<Category>> queryCategoriesByCids(
            @RequestParam("cids") List<Long> cids
    ){
        List<Category> categories  =  this.categoryService.queryCategoriesByCids(cids);
        if (categories==null||categories.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    /**
     * 查询三级分类
     * @param id
     * @return
     */
    @GetMapping("level/{id}")
    public ResponseEntity<List<Category>> queryParentByCid3(
            @PathVariable("id") Long id){
        List<Category> categories  = this.categoryService.queryParentByCid3(id);
        if (categories==null&&categories.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
