package life.sujunbin.community.community.exception;


import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/8 9:10
 */
@Data
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(ICustomizeException error)
    {

        this.code = error.getCode();
        this.message = error.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }


    public Integer getCode() {
        return code;
    }


}
