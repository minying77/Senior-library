package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.BookMapper;
import cn.edu.scnu.service.IBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.selectList(null);
    }

    @Override
    public List<Book> page(BookPageRequest bookPageRequest) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if(bookPageRequest.getName() != null){
            queryWrapper.like("name", bookPageRequest.getName());
        }

        if(bookPageRequest.getBookNo() != null){
            queryWrapper.like("bookNo", bookPageRequest.getBookNo());
        }

        Integer pageNum = bookPageRequest.getPageNum();
        Integer pageSize = bookPageRequest.getPageSize();

        Page<Book> bookPage = new Page<>(pageNum, pageSize);
        Page<Book> pageList = bookMapper.selectPage(bookPage, queryWrapper);

        return pageList.getRecords();
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.selectById(id);
    }

    @Override
    public void add(Book book) {
        try{
            bookMapper.insert(book);
        } catch (DuplicateKeyException e) {
            // 捕获新增的分类重复错误
            throw new ServiceException("图书名重复");
        }
    }

    @Override
    public void update(Book book) {
        book.setUpdatetime(new Date());
        bookMapper.updateById(book);
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }
}
