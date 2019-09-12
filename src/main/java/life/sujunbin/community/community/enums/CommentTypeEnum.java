package life.sujunbin.community.community.enums;



/**
 * @author: 苏俊滨
 * @date: 2019/9/10 21:16
 */
public enum CommentTypeEnum {

    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum :CommentTypeEnum.values())
        {
            if(commentTypeEnum.getType() ==type)
            {
                return true;
            }
        }
        return false;
    }

    public Integer getType()
    {
        return type;
    }

    CommentTypeEnum(Integer type)
    {
        this.type = type;
    }


}
