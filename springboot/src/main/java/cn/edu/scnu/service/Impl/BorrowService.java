package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.dto.BorrowPageDTO;
import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.BookMapper;
import cn.edu.scnu.mapper.BorrowMapper;
import cn.edu.scnu.mapper.UserMapper;
import cn.edu.scnu.mapper.po.BorrowReturCountPO;
import cn.edu.scnu.service.IBorrowService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
            QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("title", borrowPageRequest.getTitle());
            List<Book> books = bookMapper.selectList(queryWrapper);
            log.info("A"+books.size());
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
            borrowPageDTO.setReturnedDate(borrow.getReturnedtime());

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(borrow.getCreatetime());
            calendar.add(Calendar.DATE, borrow.getTimes());
            borrowPageDTO.setShouldReturnDate(calendar.getTime());

            Book book = bookMapper.selectById(borrow.getBookId());
            borrowPageDTO.setTitle(book.getTitle());
            borrowPageDTO.setIsbn(book.getIsbn());
            borrowPageDTO.setScore(book.getScore());
            borrowPageDTO.setPrice(book.getPrice());

            User user = userMapper.selectById(borrow.getUserId());
            borrowPageDTO.setName(user.getName());
            borrowPageDTO.setUserNo(user.getUserNo());
            borrowPageDTO.setPhone(user.getPhone());

            Date now = new Date();
            if(now.after(borrowPageDTO.getShouldReturnDate())){
                borrowPageDTO.setNote("已过期");
            }
            if(now.before(borrowPageDTO.getShouldReturnDate())){
                borrowPageDTO.setNote("未到期");
            }
            if(now.equals(borrowPageDTO.getShouldReturnDate())){
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
    @Transactional
    public void add(Borrow obj) {
        try {
            // 1.校验用户是否存在
            Integer userNo = obj.getUserId();
            User user = userMapper.getByID(userNo);
            if (Objects.isNull(user)) {
                throw new ServiceException("用户不存在");
            }

            // 2.校验图书是否存在
            Book book = bookMapper.getByNo(obj.getBookId());
            if (Objects.isNull(book)) {
                throw new ServiceException("所借图书不存在");
            }

            // 3.校验用户账户积分是否足够
            Integer account = user.getAccount();
            Integer score = book.getScore();
            if (score > account) {
                throw new ServiceException("用户积分不足");
            }

            // 4.校验图书数量
            if (book.getStoringNum() < 1) {
                throw new ServiceException("图书数量不足");
            }

            // 5.校验借书记录是否重复
            QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userNo);
            queryWrapper.eq("book_id", obj.getBookId());
            queryWrapper.ne("status", "4"); // 不包括已归还状态的记录
            List<Borrow> existingBorrows = borrowMapper.selectList(queryWrapper);
            if (!existingBorrows.isEmpty()) {
                throw new ServiceException("该书已被该用户借阅，不能重复借阅");
            }

            // 6.更新用户积分余额
            user.setAccount(user.getAccount() - score);
            userMapper.updateById(user);

            // 7.更新图书数量
            book.setStoringNum(book.getStoringNum() - 1);
            bookMapper.updateById(book);

            // 8.新增图书记录
            obj.setStatus("1"); // 设置初始状态为待审核
            obj.setCreatetime(new Date());
            obj.setUpdatetime(new Date());
            borrowMapper.insert(obj);
        } catch (Exception e) {
            throw new ServiceException("借书操作失败：" + e.getMessage());
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
    @Transactional
    public void changeStatus(Integer id, String s) {
        Borrow borrow = borrowMapper.selectById(id);
        if (borrow == null) {
            throw new ServiceException("借阅记录不存在");
        }

        // 获取当前状态
        String currentStatus = borrow.getStatus();

        // 状态转换逻辑
        // 根据传入的命令s:yes/no更新状态
        String newStatus = null;
        switch (s) {
            case "yes":
                newStatus = ("1".equals(currentStatus)) ? "2" : null; // 只有待审核状态可以变为借阅中
                break;
            case "no":
                newStatus = ("1".equals(currentStatus)) ? "3" : null; // 只有待审核状态可以变为审核不通过
                break;
            default:
                throw new ServiceException("无效的操作命令");
        }


        if (newStatus != null) {
            // 执行状态更新
            UpdateWrapper<Borrow> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("status", newStatus).eq("id", id);
            borrowMapper.update(null, updateWrapper);
        } else {
            throw new ServiceException("状态转换无效或不允许");
        }}

    @Override
    public Page<BorrowPageDTO> listByUser(Integer userId,  BaseRequest baseRequest) {
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Borrow> borrowList = borrowMapper.selectList(queryWrapper);

        List<BorrowPageDTO> borrowPageDTOList;

        borrowPageDTOList = borrowList.stream().map(borrow -> {

            BorrowPageDTO borrowPageDTO = new BorrowPageDTO();

            borrowPageDTO.setId(borrow.getId());
            borrowPageDTO.setUserId(borrow.getUserId());
            borrowPageDTO.setBookId(borrow.getBookId());
            borrowPageDTO.setStatus(borrow.getStatus());
            borrowPageDTO.setTimes(borrow.getTimes());
            borrowPageDTO.setCreatetime(borrow.getCreatetime());
            borrowPageDTO.setUpdatetime(borrow.getUpdatetime());
            borrowPageDTO.setReturnedDate(borrow.getReturnedtime());

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(borrow.getCreatetime());
            calendar.add(Calendar.DATE, borrow.getTimes());
            borrowPageDTO.setShouldReturnDate(calendar.getTime());

            Book book = bookMapper.selectById(borrow.getBookId());
            borrowPageDTO.setTitle(book.getTitle());
            borrowPageDTO.setIsbn(book.getIsbn());
            borrowPageDTO.setPrice(book.getPrice());
            return borrowPageDTO;
        }).collect(Collectors.toList());

        return listToPage(borrowPageDTOList, baseRequest.getPageNum(), baseRequest.getPageSize());

    }

    @Override
    public void returnBook(Integer id) {
        Borrow borrowRecord = borrowMapper.selectById(id);
        if (borrowRecord == null) {
            throw new ServiceException("借书记录不存在");
        }
        if ("1".equals(borrowRecord.getStatus())) {
            throw new ServiceException("借阅未审核，请勿点击");
        }
        if ("3".equals(borrowRecord.getStatus())) {
            throw new ServiceException("该借阅不通过审核，请勿点击");
        }
        if ("4".equals(borrowRecord.getStatus())) {
            throw new ServiceException("该图书已归还，请勿点击");
        }

        // 更新图书数量
        Book book = bookMapper.selectById(borrowRecord.getBookId());
        book.setStoringNum(book.getStoringNum() + 1);
        bookMapper.updateById(book);

        // 更新借书记录状态和归还时间
        borrowRecord.setStatus("4"); // 已归还
        borrowRecord.setUpdatetime(new Date());
        borrowRecord.setReturnedtime(new Date());

        //用户积分返还
        //Integer userNo = borrowRecord.getUserId();
        User user = userMapper.selectById(borrowRecord.getUserId());
        user.setAccount(user.getAccount()+book.getScore());

        borrowMapper.updateById(borrowRecord);
    }


    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String,Object> map = new HashMap<>();
        Date today =new Date();
        List<DateTime> dateRange;
        switch(timeRange){
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-29),today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-89),today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date",dateStrRange);//x轴日期数据生产完毕
        log.info("dateStrRange：{}",dateStrRange);

        List<BorrowReturCountPO> returnCount = borrowMapper.getCountByTimeRange(timeRange,4);
        List<BorrowReturCountPO> borrowCount = borrowMapper.getCountByTimeRange(timeRange,2);//状态为2的加上状态为4的才算作总借阅
        map.put("borrow", countList(borrowCount,dateStrRange));
        map.put("retur",countList(returnCount, dateStrRange));
        log.info("dateRange：{}、borrowCount：{}和 returnCount：{}",dateRange,borrowCount,returnCount);
        return map;
        }

    //对数据库未统计的时间进行处理
    private List<Integer> countList(List<BorrowReturCountPO> countPOList, List<String> dateStrRange) {
        List<Integer> list= CollUtil.newArrayList();
        if(CollUtil.isEmpty(countPOList)){
            return list;
        }
        for(String date : dateStrRange){
            //.map(BorrowReturCountPO::getCount)取出对象里的count只
            // orElse(0);对没匹配的数据返回0
            Integer count = countPOList.stream().filter(countPO ->date.equals(countPO.getDate()))
                            .map(BorrowReturCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }

    private List<String> datetimeToDateStr(List<DateTime> dateRange) {
        List<String> list= CollUtil.newArrayList();
        if(CollUtil.isEmpty(dateRange)){
            return list;
        }
        for(DateTime dateTime : dateRange){
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }
}
