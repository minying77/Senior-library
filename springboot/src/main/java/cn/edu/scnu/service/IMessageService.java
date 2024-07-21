package cn.edu.scnu.service;

import cn.edu.scnu.controller.request.MessagePageRequest;
import cn.edu.scnu.entity.Message;
import cn.edu.scnu.entity.Reply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IMessageService {

    void add(Message message);

    List<Message> list();

    Page<Message> page(MessagePageRequest messagePageRequest);

    Reply isRely(Integer id);

    void replyMessage(Reply reply);
}
