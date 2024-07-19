package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.CategoryPageRequest;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.CategoryMapper;
import cn.edu.scnu.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.selectList(null);
    }

    @Override
    public List<CategoryPageDTO> page(CategoryPageRequest categoryPageRequest) {
        QueryWrapper<Category> queryWrapper1 = new QueryWrapper<>();
        if(categoryPageRequest.getName() != null){
            queryWrapper1.like("name", categoryPageRequest.getName());
        }

        queryWrapper1.isNull("pid"); //先选出一级分类

        Integer pageNum = categoryPageRequest.getPageNum();
        Integer pageSize = categoryPageRequest.getPageSize();

        Page<Category> categoryPage = new Page<>(pageNum, pageSize);
        Page<Category> pageList = categoryMapper.selectPage(categoryPage, queryWrapper1);

        List<Category> categoryList = pageList.getRecords();
        List<CategoryPageDTO> pageDTOList;

        pageDTOList = categoryList.stream().map(category ->{
            CategoryPageDTO dto = new CategoryPageDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setRemark(category.getRemark());
            dto.setPid(category.getPid());
            dto.setCreatetime(category.getCreatetime());
            dto.setUpdatetime(category.getUpdatetime());

            QueryWrapper<Category> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("pid", dto.getId());
            dto.setChildren(categoryMapper.selectList(queryWrapper2));  //再选出二级分类

            return dto;
        }).collect(Collectors.toList());
        return pageDTOList;
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void add(Category category) {
        try{
            categoryMapper.insert(category);
        } catch (DuplicateKeyException e) {
            // 捕获新增的分类重复错误
            throw new ServiceException("图书分类名重复");
        }
    }

    @Override
    public void update(Category category) {
        category.setUpdatetime(new Date());
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
