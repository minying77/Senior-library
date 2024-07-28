package cn.edu.scnu.service;

import cn.edu.scnu.controller.dto.BorrowPageDTO;
import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Borrow;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public interface IBorrowService {
    List<Borrow> list();

    Page<BorrowPageDTO> page(BorrowPageRequest borrowPageRequest);

    Borrow getById(Integer id);

    void add(Borrow book);

    void update(Borrow book);

    void deleteById(Integer id);

    void changeStatus(Integer id, String s);

    Page<BorrowPageDTO> listByUser(Integer userId, BaseRequest baseRequest);

    void returnBook(Integer id);

    Map<String, Object> getCountByTimeRange(String timeRange);
}
