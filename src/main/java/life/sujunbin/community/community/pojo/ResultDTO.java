package life.sujunbin.community.community.pojo;

import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 16:18
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
    public static ResultDTO errofof(Integer code,String message)
    {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errofof(CustomizeErrorcode errorcode) {
        return errofof(errorcode.getCode(), errorcode.getMessage());

    }
    public static ResultDTO okof()
    {
           return errofof(200, "请求成功");
    }
    public static ResultDTO errofof(CustomizeException error) {
        return errofof(error.getCode(), error.getMessage());

    }
    public static <T>ResultDTO okof(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }


}
