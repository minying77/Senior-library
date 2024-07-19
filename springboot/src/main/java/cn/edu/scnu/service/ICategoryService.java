package cn.edu.scnu.service;

import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.AdminPageRequest;
import cn.edu.scnu.controller.request.CategoryPageRequest;
import cn.edu.scnu.entity.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> list();

    List<CategoryPageDTO> page(CategoryPageRequest categoryPageRequest);

    Category getById(Integer id);

    void add(Category category);

    void update(Category category);

    void deleteById(Integer id);
}
