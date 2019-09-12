package life.sujunbin.community.community.pojo;

import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 16:18
 */
@Data
public class ResultDTO {
    private Integer code;
    private String massage;
    public static ResultDTO errofof(Integer code,String massage)
    {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMassage(massage);
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

}
