package cn.edu.scnu.service;

import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.entity.Book;

import java.util.List;

public interface IBookService {
    List<Book> list();

    List<Book> page(BookPageRequest bookPageRequest);

    Book getById(Integer id);

    void add(Book book);

    void update(Book book);

    void deleteById(Integer id);
}
