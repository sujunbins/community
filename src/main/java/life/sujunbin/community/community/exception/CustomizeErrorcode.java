package life.sujunbin.community.community.exception;

/**
 * @author: 苏俊滨
 * @date: 2019/9/8 9:11
 */
public enum  CustomizeErrorcode implements ICustomizeException {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了要不要换个问题试试"),

    TARGET_PARAM_NOT_FOUND(2001,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后进行操作"),
    SYSTEM_ERROR(2004,"服务器冒烟了，要不再试试！！！"),
    TYPE_NOT_FOUND(2005,"评论类型不匹配"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在，要不要再试试" );
    private String message;

    private Integer code;

    CustomizeErrorcode(Integer code ,String message)
    {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }}
