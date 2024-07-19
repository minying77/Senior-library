package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.AdminPageRequest;
import cn.edu.scnu.controller.request.CategoryPageRequest;
import cn.edu.scnu.entity.Admin;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书分类管理类:
 * 功能：增（增加一级分类、增加二级分类）
 *      删
 *      改（改只能修改name、remark；id和pid不可修改）
 *      查（分页查询）
 */

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    /**
     * 查询所有图书分类
     * @return list
     */
    @GetMapping("/list")
    public Result list(){
        List<Category> list =  categoryService.list();
        return Result.success(list);
    }

    /**
     * 分页查询分类
     * @return 该页dto对象格式的list
     */
    @GetMapping("/page")
    public Result page(@RequestBody CategoryPageRequest categoryPageRequest){
        List<CategoryPageDTO> list= categoryService.page(categoryPageRequest);  //page(当前页码, 每页记录数)
        return Result.success(list);
    }

    /**
     * 根据id查询分类
     * @param id
     * @return category
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 新增分类（注意：一级分类pid=null，二级分类pid=一级分类的id）
     * @param category 分类信息
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody Category category){

        categoryService.add(category);
        return Result.success();
    }



    /**
     * 修改分类
     * @param category
     * @return Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id 分类id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }


}
