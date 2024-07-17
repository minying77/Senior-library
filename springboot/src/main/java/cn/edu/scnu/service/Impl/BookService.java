package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.dto.BookDTO;
import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.BookMapper;
import cn.edu.scnu.mapper.CategoryMapper;
import cn.edu.scnu.service.IBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Book> list() {
        return bookMapper.selectList(null);
    }

    @Override
    public Page<BookDTO> page(BookPageRequest bookPageRequest) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if(bookPageRequest.getTitle() != null){
            queryWrapper.like("title", bookPageRequest.getTitle());
        }

        if(bookPageRequest.getISBN() != null){
            queryWrapper.like("isbn", bookPageRequest.getISBN());
        }

        Integer pageNum = bookPageRequest.getPageNum();
        Integer pageSize = bookPageRequest.getPageSize();

        List<Book> books = bookMapper.selectList(queryWrapper);
        List<BookDTO> bookDTOS;

        bookDTOS = books.stream().map(book ->{
            BookDTO dto = new BookDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setPublishDate(book.getPublishDate());
            dto.setPublisher(book.getPublisher());
            dto.setIsbn(book.getIsbn());
            dto.setDescription(book.getDescription());
            dto.setCover(book.getCover());
            dto.setCreatetime(book.getCreatetime());
            dto.setUpdatetime(book.getUpdatetime());
            dto.setPrice(book.getPrice());
            dto.setScore(book.getScore());
            dto.setStoreNum(book.getStoreNum());
            dto.setStoringNum(book.getStoringNum());

            Category category1 = categoryMapper.selectById(book.getCategoryId());
            log.info(category1.getId()+" "+category1.getPid());
            Category category2 = categoryMapper.selectById(category1.getPid());
            dto.setCategory(category2.getName()+ "/" +category1.getName());  //再选出二级分类

            return dto;
        }).collect(Collectors.toList());


        return listToPage(bookDTOS, pageNum, pageSize);
    }

    private Page<BookDTO> listToPage(List list, int pageNum, int pageSize){
        List pageList = new ArrayList<>();
        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        Page<BookDTO> page = new Page<>(pageNum, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.selectById(id);
    }

    @Override
    public void add(BookDTO bookDTO) {
        try{

            log.info(bookDTO.toString());
            List<String> categories = bookDTO.getCategories();
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", categories.get(1));
            Category category = categoryMapper.selectOne(queryWrapper);
            Book book = new Book();
            //BeanUtils.copyProperties(book, bookDTO);
            book.setCategoryId(category.getId());
            book.setCover(bookDTO.getCover());
            book.setAuthor(bookDTO.getAuthor());
            book.setDescription(bookDTO.getDescription());
            book.setPrice(bookDTO.getPrice());
            book.setScore(bookDTO.getScore());
            book.setPublisher(bookDTO.getPublisher());
            book.setPublishDate(bookDTO.getPublishDate());
            book.setStoreNum(bookDTO.getStoreNum());
            book.setStoringNum(bookDTO.getStoreNum());
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());

            log.info(book.toString());
            bookMapper.insert(book);

        } catch (DuplicateKeyException e) {
            // 捕获新增的分类重复错误
            throw new ServiceException("图书名重复");
        }
    }

    @Override
    public void update(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setCover(bookDTO.getCover());
        book.setAuthor(bookDTO.getAuthor());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());
        book.setScore(bookDTO.getScore());
        book.setPublisher(bookDTO.getPublisher());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setStoreNum(bookDTO.getStoreNum());
        book.setStoringNum(bookDTO.getStoringNum());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        String category = bookDTO.getCategories().get(1);
        Category category1 = categoryMapper.selectOne(new QueryWrapper<Category>().eq("name", category));
        book.setCategoryId(category1.getId());
        log.info(book.toString());
        bookMapper.updateById(book);
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }
}
