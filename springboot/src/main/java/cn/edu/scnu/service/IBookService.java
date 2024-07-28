package cn.edu.scnu.service;

import cn.edu.scnu.controller.dto.BookDTO;
import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.entity.Book;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IBookService {
    List<Book> list();

    Page page(BookPageRequest bookPageRequest);

    Book getById(Integer id);

    void add(BookDTO bookDTO);

    void update(BookDTO bookDTO);

    void deleteById(Integer id);

    List<Book> listByCategory(Integer categoryId);
}
