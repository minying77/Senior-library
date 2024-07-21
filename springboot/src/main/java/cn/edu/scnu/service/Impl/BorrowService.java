package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.dto.BorrowPageDTO;
import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.BookMapper;
import cn.edu.scnu.mapper.BorrowMapper;
import cn.edu.scnu.mapper.UserMapper;
import cn.edu.scnu.service.IBorrowService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BorrowService implements IBorrowService {

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    BookMapper bookMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.selectList(null);
    }

    @Override
    public Page<BorrowPageDTO> page(BorrowPageRequest borrowPageRequest) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();

        if(StrUtil.isNotEmpty(borrowPageRequest.getTitle())){
            log.info(borrowPageRequest.getTitle());
            List<Book> books = bookMapper.selectList(new QueryWrapper<Book>().like("title", borrowPageRequest.getTitle()));
            log.info(books.toString());
            List<Integer> bookIdList = new ArrayList<>();
            for(Book book : books){
                bookIdList.add(book.getId());
            }
            borrowQueryWrapper.in("book_id", bookIdList);
        }

        if(StrUtil.isNotEmpty(borrowPageRequest.getName())){
            log.info(borrowPageRequest.getName());
            List<User> userList = userMapper.selectList(new QueryWrapper<User>().like("name", borrowPageRequest.getName()));
            List<Integer> idList = new ArrayList<>();
            for(User user : userList){
                idList.add(user.getId());
            }
            borrowQueryWrapper.in("user_id", idList);

        }

        Integer pageNum = borrowPageRequest.getPageNum();
        Integer pageSize = borrowPageRequest.getPageSize();

        // Page<Borrow> borrowPage = new Page<>(pageNum, pageSize);
        List<Borrow> borrowList = borrowMapper.selectList(borrowQueryWrapper);
        log.info(borrowList.toString());

        List<BorrowPageDTO> pageDTOList;

        pageDTOList = borrowList.stream().map(borrow -> {
            BorrowPageDTO borrowPageDTO = new BorrowPageDTO();
            borrowPageDTO.setId(borrow.getId());
            borrowPageDTO.setUserId(borrow.getUserId());
            borrowPageDTO.setBookId(borrow.getBookId());
            borrowPageDTO.setStatus(borrow.getStatus());
            borrowPageDTO.setTimes(borrow.getTimes());
            borrowPageDTO.setCreatetime(borrow.getCreatetime());
            borrowPageDTO.setUpdatetime(borrow.getUpdatetime());

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(borrow.getCreatetime());
            calendar.add(Calendar.DATE, borrow.getTimes());
            borrowPageDTO.setReturnDate(calendar.getTime());

            Book book = bookMapper.selectOne(new QueryWrapper<Book>().eq("id", borrow.getBookId()));
            borrowPageDTO.setTitle(book.getTitle());
            borrowPageDTO.setIsbn(book.getIsbn());
            borrowPageDTO.setScore(book.getScore());
            borrowPageDTO.setPrice(book.getPrice());

            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", borrow.getUserId()));
            borrowPageDTO.setName(user.getName());
            borrowPageDTO.setUserNo(user.getUserNo());
            borrowPageDTO.setPhone(user.getPhone());

            Date now = new Date();
            if(now.after(borrowPageDTO.getReturnDate())){
                borrowPageDTO.setNote("已过期");
            }
            if(now.before(borrowPageDTO.getReturnDate())){
                borrowPageDTO.setNote("未到期");
            }
            if(now.equals(borrowPageDTO.getReturnDate())){
                borrowPageDTO.setNote("已到期");
            }

            return borrowPageDTO;
        }).collect(Collectors.toList());

        return listToPage(pageDTOList, pageNum, pageSize);
    }

    private Page<BorrowPageDTO> listToPage(List list, int pageNum, int pageSize){
        List pageList = new ArrayList<>();
        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        Page<BorrowPageDTO> page = new Page<>(pageNum, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.selectById(id);
    }

    @Override
    public void add(Borrow borrow) {
        try{
            borrowMapper.insert(borrow);
        } catch (DuplicateKeyException e) {
            // 捕获新增的分类重复错误
            throw new ServiceException("借书重复");
        }
    }

    @Override
    public void update(Borrow borrow) {
        borrow.setUpdatetime(new Date());
        borrowMapper.updateById(borrow);
    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    @Override
    public void changeStatus(Integer id, String s) {
        UpdateWrapper<Borrow> updateWrapper = new UpdateWrapper<>();
        if(s.equals("yes")){
            updateWrapper.set("status", 2).eq("id", id);
        } else if (s.equals("no")) {
            updateWrapper.set("status", 3).eq("id", id);
        }

        borrowMapper.update(null, updateWrapper);
    }
}
