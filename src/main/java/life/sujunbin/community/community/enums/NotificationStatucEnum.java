package life.sujunbin.community.community.enums;

/**
 * @author: 苏俊滨
 * @date: 2019/9/17 8:15
 */
public enum NotificationStatucEnum {
    UNREAD(0),
    READ(1);
    private int statuc;
    public int gerStatuc()
    {
        return statuc;
    }
     NotificationStatucEnum(int statuc)
    {
        this.statuc = statuc;
    }



}
