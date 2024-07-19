package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.BorrowMapper;
import cn.edu.scnu.service.IBorrowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class BorrowService implements IBorrowService {

    @Resource
    BorrowMapper borrowMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.selectList(null);
    }

    @Override
    public List<Borrow> page(BorrowPageRequest borrowPageRequest) {
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        if(borrowPageRequest.getBookName() != null){
            queryWrapper.like("bookName", borrowPageRequest.getBookName());
        }

        if(borrowPageRequest.getBookNo() != null){
            queryWrapper.eq("bookNo", borrowPageRequest.getBookNo());
        }

        if(borrowPageRequest.getUsername() != null){
           queryWrapper.in("user_id", "select username from user where name=borrowPageRequest.getUsername()");

        }
        Integer pageNum = borrowPageRequest.getPageNum();
        Integer pageSize = borrowPageRequest.getPageSize();

        Page<Borrow> borrowPage = new Page<>(pageNum, pageSize);
        Page<Borrow> pageList = borrowMapper.selectPage(borrowPage, queryWrapper);

        return pageList.getRecords();


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
}
