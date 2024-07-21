package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.request.MessagePageRequest;
import cn.edu.scnu.entity.Message;
import cn.edu.scnu.entity.Reply;
import cn.edu.scnu.mapper.MessageMapper;
import cn.edu.scnu.mapper.ReplyMapper;
import cn.edu.scnu.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Resource
    MessageMapper messageMapper;

    @Resource
    ReplyMapper replyMapper;

    @Override
    public void add(Message message) {
        message.setStatus("0");
        messageMapper.insert(message);
    }

    @Override
    public List<Message> list() {
        return messageMapper.selectList(null);
    }

    @Override
    public Page<Message> page(MessagePageRequest messagePageRequest) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messagePageRequest.getUserId());

        Integer pageNum = messagePageRequest.getPageNum();
        Integer pageSize = messagePageRequest.getPageSize();

        Page<Message> messagePage = new Page<>(pageNum, pageSize);
        Page<Message> messageList = messageMapper.selectPage(messagePage, queryWrapper);

        return messageList;
    }

    @Override
    public Reply isRely(Integer id) {
        Message message = messageMapper.selectById(id);
        if(message.getStatus().equals("0")){
            return null;
        }

        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.eq("message_id", id);
        return replyMapper.selectOne(replyQueryWrapper);

    }

    @Override
    public void replyMessage(Reply reply) {
        replyMapper.insert(reply);
        Message message = messageMapper.selectOne(new QueryWrapper<Message>().eq("id", reply.getMessageId()));
        message.setStatus("1");
        messageMapper.updateById(message);
    }
}
