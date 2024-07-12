package cn.edu.scnu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;//响应码，1 代表成功; 0 代表失败
    private Object data; //返回的数据
    private String msg;  //响应信息 描述字符串

    //增删改 成功响应
    public static Result success(){
        return new Result("1",null,"success");
    }

    //查询 成功响应
    public static Result success(Object data){
        return new Result("1", data,"success");
    }

    //失败响应
    public static Result error(String msg){
        return new Result("0", null,msg);
    }

    public static Result error(String code, String msg){
        return new Result( code, null, msg);
    }
}
