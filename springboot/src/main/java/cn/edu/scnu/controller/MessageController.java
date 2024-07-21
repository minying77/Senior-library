package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.request.MessagePageRequest;
import cn.edu.scnu.entity.Message;
import cn.edu.scnu.entity.Reply;
import cn.edu.scnu.service.IMessageService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    IMessageService messageService;

    /**
     * 所有留言list
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        List<Message> messageList = messageService.list();
        return Result.success(messageList);
    }

    /**
     * 根据用户id分页查询其留言
     * @param messagePageRequest
     * @return
     */
    @PostMapping("page")
    public Result page(@RequestBody MessagePageRequest messagePageRequest){
       Page<Message> list =  messageService.page(messagePageRequest);
       return Result.success(list);
    }


    /**
     * 新增留言记录
     * @param message
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Message message){
        messageService.add(message);
        return Result.success();
    }

    /**
     * 根据留言id 查询回复信息
     * @param id
     * @return
     */
    @PostMapping("/isReply/{id}")
    public Result isReply(@PathVariable Integer id){
       Reply reply =  messageService.isRely(id);
       if(reply != null){
           return Result.success(reply.getContent());
       }
       return Result.success("该留言暂未回复");
    }

    /**
     * admin 回复留言
     * @param reply
     * @return
     */
    @PostMapping("/replyMessage")
    public Result replyMessage(@RequestBody Reply reply){
        messageService.replyMessage(reply);
        return Result.success();
    }
}
