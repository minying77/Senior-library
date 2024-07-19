package cn.edu.scnu.service;

import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Borrow;

import java.util.List;

public interface IBorrowService {
    List<Borrow> list();

    List<Borrow> page(BorrowPageRequest borrowPageRequest);

    Borrow getById(Integer id);

    void add(Borrow book);

    void update(Borrow book);

    void deleteById(Integer id);

}
